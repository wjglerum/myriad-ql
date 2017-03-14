# coding=utf-8
from pql.traversal.ExpressionVisitor import ExpressionVisitor
from pql.traversal.FormVisitor import FormVisitor
from pql.traversal.IdentifierVisitor import IdentifierVisitor
from pql.typechecker.types import DataTypes


class TypeChecker(FormVisitor, ExpressionVisitor, IdentifierVisitor):
    def __init__(self, ast, environment_type):
        self.symbol_table = environment_type(ast).visit()
        self.ast = ast
        self.errors = list()

    def visit(self):
        self.errors.clear()
        [form.apply(self) for form in self.ast]
        return self.errors

    def form(self, node):
        [statement.apply(self) for statement in node.statements]

    def field(self, node):
        if node.expression is not None:
            result = node.expression.apply(self)
            if result is not None and node.data_type.data_type is DataTypes.boolean and (result is not node.data_type.data_type):
                self.errors.append("Expression of field [{}] did not match declared type [{}], at the following location: {}"
                                   .format(result, node.data_type.data_type, node.expression.location))

    def subtraction(self, node):
        return self.type_detection(node, self.arithmetic_type_detection)

    def division(self, node):
        return self.type_detection(node, self.arithmetic_type_detection)

    def multiplication(self, node):
        return self.type_detection(node, self.arithmetic_type_detection)

    def addition(self, node):
        return self.type_detection(node, self.arithmetic_type_detection)

    def conditional_if(self, node):
        condition_result = node.condition.apply(self)
        if condition_result.data_type is not DataTypes.boolean:
            self.errors.append(
                "Condition on location {} does not contain a boolean expression, it's result type is {}".format(
                    node.location, condition_result))
        [statement.apply(self) for statement in node.statements]

    def conditional_if_else(self, node):
        condition_result = node.condition.apply(self)
        if condition_result is None:
            self.errors.append(
                "Condition on location {} does not contain a valid boolean expression".format(node.condition.location))
        elif condition_result.data_type is not DataTypes.boolean:
            self.errors.append(
                "Condition on location {} does not contain a valid boolean expression, it's result type is {}".format(
                    node.condition.location, str(condition_result.data_type)))
        [statement.apply(self) for statement in node.statements]
        [statement.apply(self) for statement in node.else_statement_list]

    def greater_exclusive(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def greater_inclusive(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def lower_inclusive(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def lower_exclusive(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def equality(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def inequality(self, node):
        return self.type_detection(node, self.boolean_type_detection)

    def and_(self, node):
        return self.type_detection(node, self.boolean_type_detection, allowed_arithmetic_types=set())

    def or_(self, node):
        return self.type_detection(node, self.boolean_type_detection, allowed_arithmetic_types=set())

    def negation(self, node):
        if node.operand.apply(self) is DataTypes.boolean:
            return DataTypes.boolean
        self.errors.append("Negation was passed a non-boolean value on location {} ".format(node.location))
        return None

    def positive(self, node):
        result = node.operand.apply(self)
        if result is (DataTypes.integer or DataTypes.money):
            return result
        self.errors.append("Positive was passed a non-numeric value on location {} ".format(node.location))
        return None

    def negative(self, node):
        result = node.operand.apply(self)
        if result is (DataTypes.integer or DataTypes.money):
            return result
        self.errors.append("Negative was passed a non-numeric value on location {} ".format(node.location))
        return None

    def arithmetic_type_detection(self, allowed_arithmetic_types, _, type_set):
        dominant_type = None
        type_set_data_types = {d_type.data_type for d_type in type_set if d_type is not None}
        if type_set_data_types.issubset(allowed_arithmetic_types):
            if DataTypes.money in type_set_data_types:
                dominant_type = DataTypes.money
            else:
                dominant_type = DataTypes.integer
        else:
            self.add_leaf_error(allowed_arithmetic_types, type_set)
        return dominant_type

    def boolean_type_detection(self, allowed_arithmetic_types, allowed_boolean_types, type_set):
        dominant_type = None

        allowed_types = allowed_arithmetic_types.union(allowed_boolean_types)
        type_set_data_types = {d_type.data_type for d_type in type_set if d_type is not None}
        if type_set_data_types.issubset(allowed_types):
            if type_set_data_types.issubset(allowed_arithmetic_types):
                dominant_type = DataTypes.boolean
            elif type_set_data_types.issubset(allowed_boolean_types):
                dominant_type = DataTypes.boolean
            else:
                self.add_leaf_error(allowed_types, type_set)
        else:
            self.add_leaf_error(allowed_types, type_set)

        return dominant_type

    def add_leaf_error(self, allowed_types, type_set):
        self.errors.append("Type mismatch: the following types were incompatible {}, only {} is allowed "
                           .format(['{}: {}'.format(t.location, t.data_type) for t in type_set],
                                   [str(a) for a in allowed_types]))

    def type_detection(self, node, func, allowed_arithmetic_types={DataTypes.integer, DataTypes.money},
                       allowed_boolean_types={DataTypes.boolean}):
        type_set = {node.lhs.apply(self), node.rhs.apply(self)}
        return func(allowed_arithmetic_types, allowed_boolean_types, type_set)

    def identifier(self, node):
        return self.symbol_table[node.name]

    def value(self, node):
        return node
