require 'parslet'

class Type
  def self.descendants
    ObjectSpace.each_object(Class).select { |klass| klass < self }
  end
end

class BooleanType < Type
  def self.type
    'boolean'
  end
end

class IntegerType < Type
  def self.type
    'integer'
  end
end

class DateType < Type
  def self.type
    'date'
  end
end

class DecimalType < Type
  def self.type
    'decimal'
  end
end

class StringType < Type
  def self.type
    'string'
  end
end

class MoneyType < Type
  def self.type
    'money'
  end
end


module TypeParser
  include Parslet

  rule(:type) do
    Type.descendants.map { |type| str(type.type) }.reduce(&:|).as(:type) >> spaces?
  end
end