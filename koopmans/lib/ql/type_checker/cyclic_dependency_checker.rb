module QL
  module TypeChecker
    class CyclicDependencyChecker
      def visit_form(form, collected_data=nil)
        @variable_dependencies = collected_data
        form.statements.map { |statement| statement.accept(self) }
      end

      # nothing has to be done with a question
      def visit_question(_)
      end

      # visit the assignment of a computed question
      def visit_computed_question(computed_question)
        computed_question.assignment.accept(self)
      end

      # only visit all statements of the if statement because nothing has to be done with the condition
      def visit_if_statement(if_statement)
        if_statement.body.map { |statement| statement.accept(self) }
      end

      def visit_if_else_statement(if_else_statement)
        if_body_variables = if_else_statement.if_body.map { |statement| statement.accept(self) }
        else_body_variables = if_else_statement.else_body.map { |statement| statement.accept(self) }
        [if_body_variables, else_body_variables]
      end

      # visit operation in expression
      def visit_expression(expression)
        if expression.expression.respond_to? :reduce
          expression.expression.reduce do |left, operation|
            operation.accept(left, self)
          end
        else
          expression.expression.accept(self)
        end
      end

      def visit_negation(negation)
        negation.expression.accept(self)
      end

      def visit_binary_expression(left, binary_expression)
        left.accept(self)
        binary_expression.expression.accept(self)
      end

      def visit_literal(literal)
        literal
      end

      def visit_variable(variable)
        cyclic_dependency_check(variable)
      end

      # check if the visited variable is in the dependency hash
      # for each of the dependencies, check their dependencies
      # check if the variable from the dependency is in the dependency hash
      # add new dependency to original dependency hash, don't add duplicates
      # check for cyclic dependency if there is a dependency on itself, else visit the next variable
      def cyclic_dependency_check(variable)
        dependent_variables = @variable_dependencies[variable.name]
        if dependent_variables
          dependent_variables.each do |dependent_variable|
            next_dependent_variables = @variable_dependencies[dependent_variable.name]
            if next_dependent_variables
              @variable_dependencies[variable.name] = dependent_variables | next_dependent_variables
              if @variable_dependencies[variable.name].map(&:name).include?(variable.name)
                NotificationTable.store(Notification::Error.new("question '#{variable.name}' has a cyclic dependency"))
              else
                visit_variable(dependent_variable)
              end
            end
          end
        end
        # return variable for the sake of .accept
        variable
      end
    end
  end
end