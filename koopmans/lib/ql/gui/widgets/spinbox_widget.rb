module QL
  module GUI
    class SpinboxWidget < Widget
      def initialize(options = nil)
        @default_value = 0

        @minimum = 0
        @maximum = 100

        if options
          @minimum = options[:minimum]
          @maximum = options[:maximum]
        end
      end

      def render(tk_frame)
        spinbox = TkSpinbox.new(tk_frame).pack
        spinbox.from = @minimum
        spinbox.to = @maximum
        spinbox.value = @default_value
        spinbox.command = proc { callback(spinbox.value) }
      end
    end
  end
end