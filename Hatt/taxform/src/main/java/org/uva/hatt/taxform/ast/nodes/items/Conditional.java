package org.uva.hatt.taxform.ast.nodes.items;

import org.uva.hatt.taxform.ast.nodes.ASTNode;
import org.uva.hatt.taxform.ast.nodes.expressions.Expression;

import java.util.List;

public class Conditional extends ASTNode implements Item{

    private Expression condition;
    private List<Item> thenStatements;
    private List<Item> elseStatements;

    public Conditional(int lineNumber) {
        super(lineNumber);
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public List<Item> getThenStatements() {
        return thenStatements;
    }

    public void setThenStatements(List<Item> thenStatements) {
        this.thenStatements = thenStatements;
    }

    public List<Item> getElseStatements() {
        return elseStatements;
    }

    public void setElseStatements(List<Item> elseStatements) {
        this.elseStatements = elseStatements;
    }
}
