package org.ql.evaluator.value;

public class UnknownValue extends Value {
    @Override
    public Object getPlainValue() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return !(o == null || getClass() != o.getClass());

    }

    @Override
    public boolean isUnknown() {
        return true;
    }

    @Override
    public boolean toBoolean() {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
