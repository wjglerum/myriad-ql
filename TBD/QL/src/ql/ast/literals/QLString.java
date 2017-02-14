package ql.ast.literals;

import ql.ast.ASTNode;
import ql.ast.QLLiteral;
import ql.ast.visistor.ASTVisitor;

/**
 * Created by Erik on 7-2-2017.
 */
public class QLString implements ASTNode, QLLiteral {
    private String qlString;

    public QLString(String qlString){
        this.qlString = qlString;
    }

    public String getQlString() {
        return qlString;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
