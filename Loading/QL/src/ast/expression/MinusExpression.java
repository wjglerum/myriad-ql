package ast.expression;

import ast.Visitor;
import ast.atom.Atom;

public class MinusExpression extends UnaryExpression {

	@Override
	public void accept(Visitor v) {
		v.visit(this);
		
	}

	@Override
	public Atom evaluate() {
		return getLhs().min();
	}

}
