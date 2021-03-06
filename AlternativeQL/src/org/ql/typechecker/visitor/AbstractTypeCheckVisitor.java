package org.ql.typechecker.visitor;

import org.ql.ast.ASTVisitor;
import org.ql.ast.expression.Expression;
import org.ql.ast.statement.Statement;

import java.util.List;

public abstract class AbstractTypeCheckVisitor<T, C> implements ASTVisitor<T, C> {

    T visitExpression(Expression condition, C context) {
        return condition.accept(this, context);
    }

    T visitStatements(List<Statement> statements, C context) {
        for (Statement statement : statements) {
            statement.accept(this, context);
        }

        return null;
    }
}
