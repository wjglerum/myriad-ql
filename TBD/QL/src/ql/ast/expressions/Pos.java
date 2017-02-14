package ql.ast.expressions;

import ql.ast.ASTNode;
import ql.ast.Expr;
import ql.ast.visistor.ASTVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class Pos implements ASTNode, Expr {
    private Expr expr;

    public Pos(Expr expr){
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }


    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
