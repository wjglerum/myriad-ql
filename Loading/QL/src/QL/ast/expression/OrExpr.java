package QL.ast.expression;

import QL.ast.ExpressionVisitor;

public class OrExpr extends BinaryExpr {

	public OrExpr(Expression lhs, Expression rhs, int line) {
		super(lhs, rhs, line);
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> v) {
		return v.visit(this);
	}
}
