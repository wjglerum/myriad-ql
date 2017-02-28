module QL
  module GUI
    class Question
      attr_accessor :gui
      attr_accessor :label
      attr_accessor :frame
      attr_accessor :enabled
      attr_accessor :variable
      attr_accessor :condition

      def initialize(args)
        @gui       = args[:gui]
        @label     = args[:label]
        @condition = args[:condition]

        @enabled  = true
        @variable = QL::GUI::Variable.new
        @gui.questions[args[:id]] = self

        Frame.new(question: self)
        Label.new(question: self)

        check_condition
      end

      def value
        @variable.eval
      end

      def to_json
        { @label => value }
      end

      def reload
        check_condition
      end

      def check_condition
        @condition.eval ? enable : disable if @condition
      end

      def disable
        @frame.grid_remove
        @enabled = false
      end

      def enable
        @frame.grid
        @enabled = true
      end
    end
  end
end