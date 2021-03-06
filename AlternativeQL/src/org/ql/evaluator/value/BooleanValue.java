package org.ql.evaluator.value;

public class BooleanValue extends Value {
    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public BooleanValue or(Value value) {
        return new BooleanValue(this.value || ((BooleanValue) value).getPlainValue());
    }

    @Override
    public BooleanValue and(Value value) {
        return new BooleanValue(this.value && ((BooleanValue) value).getPlainValue());
    }

    @Override
    public BooleanValue notEqual(Value comparable) {
        return new BooleanValue(value != (Boolean) comparable.getPlainValue());
    }

    @Override
    public BooleanValue equal(Value comparable) {
        return new BooleanValue(value == (Boolean) comparable.getPlainValue());
    }

    @Override
    public BooleanValue negation() {
        return new BooleanValue(!value);
    }

    public Boolean getPlainValue() {
        return value;
    }

    @Override
    public boolean toBoolean() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanValue that = (BooleanValue) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
