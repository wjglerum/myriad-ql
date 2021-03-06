package com.matthewchapman.ql.ast.expression.binary;

import com.matthewchapman.ql.ast.Expression;

/**
 * Created by matt on 27/02/2017.
 */
public abstract class BinaryOperation extends Expression {

    private final Expression left;
    private final Expression right;

    BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return this.left;
    }

    public Expression getRight() {
        return this.right;
    }
}
