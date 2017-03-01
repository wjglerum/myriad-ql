/**
 * MoneyType.java.
 */

package ql.astnodes.types;

import ql.astnodes.LineNumber;
import ql.astnodes.visitors.TypeVisitor;
import ql.gui.formenvironment.values.MoneyValue;

import java.math.BigDecimal;

public class MoneyType extends Type {

    public MoneyType() {
        super();
    }

    public MoneyType(LineNumber lineNumber) {
        super(lineNumber);
    }

    @Override
    public String toString() {
        return "Money";
    }

    @Override
    public <T> T accept(TypeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
