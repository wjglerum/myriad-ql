package org.ql.ast.expression.arithmetic;

import org.ql.ast.expression.Expression;
import org.ql.ast.expression.BinaryExpression;
import org.ql.ast.expression.ExpressionVisitor;

public class Division extends BinaryExpression {

    public Division(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "(" + getLeft() + "/" + getRight() + ")";
    }

    @Override
    public <T, C> T accept(ExpressionVisitor<T, C> visitor, C context) {
        return visitor.visitDivision(this, context);
    }
}
