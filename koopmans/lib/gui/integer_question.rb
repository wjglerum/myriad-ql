class IntegerQuestion < TextQuestion

  def initialize(args)
    super
    @variable.value = (0)
    @variable.type = IntegerType
    @previous_value = value
  end
end