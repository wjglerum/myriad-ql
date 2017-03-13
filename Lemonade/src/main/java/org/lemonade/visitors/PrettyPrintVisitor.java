package org.lemonade.visitors;

import org.lemonade.nodes.ASTNode;
import org.lemonade.nodes.Body;
import org.lemonade.nodes.Conditional;
import org.lemonade.nodes.Form;
import org.lemonade.nodes.Question;
import org.lemonade.nodes.expressions.binary.AndBinary;
import org.lemonade.nodes.expressions.binary.DivideBinary;
import org.lemonade.nodes.expressions.binary.EqBinary;
import org.lemonade.nodes.expressions.binary.GTBinary;
import org.lemonade.nodes.expressions.binary.GTEBinary;
import org.lemonade.nodes.expressions.binary.LTBinary;
import org.lemonade.nodes.expressions.binary.LTEBinary;
import org.lemonade.nodes.expressions.binary.MinusBinary;
import org.lemonade.nodes.expressions.binary.NEqBinary;
import org.lemonade.nodes.expressions.binary.OrBinary;
import org.lemonade.nodes.expressions.binary.PlusBinary;
import org.lemonade.nodes.expressions.binary.ProductBinary;
import org.lemonade.nodes.expressions.literal.BooleanLiteral;
import org.lemonade.nodes.expressions.literal.DateLiteral;
import org.lemonade.nodes.expressions.literal.DecimalLiteral;
import org.lemonade.nodes.expressions.literal.IdentifierLiteral;
import org.lemonade.nodes.expressions.literal.IntegerLiteral;
import org.lemonade.nodes.expressions.literal.MoneyLiteral;
import org.lemonade.nodes.expressions.literal.StringLiteral;
import org.lemonade.nodes.expressions.unary.BangUnary;
import org.lemonade.nodes.expressions.unary.NegUnary;
import org.lemonade.nodes.types.QLBooleanType;
import org.lemonade.nodes.types.QLDateType;
import org.lemonade.nodes.types.QLDecimalType;
import org.lemonade.nodes.types.QLIntegerType;
import org.lemonade.nodes.types.QLMoneyType;
import org.lemonade.nodes.types.QLStringType;
import org.lemonade.nodes.types.QLType;
import org.lemonade.visitors.interfaces.BaseVisitor;
import org.lemonade.visitors.interfaces.ExpressionVisitor;
import org.lemonade.visitors.interfaces.LiteralVisitor;
import org.lemonade.visitors.interfaces.TypeVisitor;

/**
 *
 */
public class PrettyPrintVisitor implements BaseVisitor<ASTNode>, ExpressionVisitor<ASTNode>, LiteralVisitor<ASTNode>, TypeVisitor<ASTNode> {
    private String tabLevel = "";

    public ASTNode visit(Form form) {
        String identifier = form.getIdentifier().getValue();
        String formStr = String.format("form %s {\n", identifier);
        System.err.print(formStr);
        for (Body body : form.getBodies()) {
            body.accept(this);
            System.err.print(tabLevel.length() + "~~~\n");
        }
        System.err.print("\n}");
        return form;

    }

    @Override
    public ASTNode visit(Question question) {
        tabLevel += "\t";
        IdentifierLiteral identifier = question.getIdentifier();
        String label = question.getLabel();
        QLType type = question.getType();
        String questionStr = String.format("%s%s: \"%s\" %s", tabLevel, identifier.getValue(), label, type);
        System.err.print(questionStr);
        tabLevel.replaceFirst("\t", "");
        return question;
    }

    @Override
    public ASTNode visit(Conditional conditional) {
        tabLevel += "\t";
        System.err.print(tabLevel + "if (");
        conditional.getCondition().accept(this);
        System.err.print(") {\n");
        for (Body body : conditional.getBodies()) {
            body.accept(this);
            System.err.print("\n");
        }
        tabLevel.replaceFirst("\t", "");
        System.err.print(tabLevel + "}");
        return conditional;
    }

    @Override
    public ASTNode visit(AndBinary andBinary) {
        return null;
    }

    @Override
    public ASTNode visit(OrBinary orBinary) {
        orBinary.getLeft().accept(this);
        System.err.print(" || ");
        orBinary.getRight().accept(this);
        return orBinary;
    }

    @Override
    public ASTNode visit(PlusBinary plusBinary) {
        plusBinary.getLeft().accept(this);
        System.err.print(" + ");
        plusBinary.getRight().accept(this);
        return plusBinary;
    }

    @Override
    public ASTNode visit(ProductBinary productBinary) {
        productBinary.getLeft().accept(this);
        System.err.print(" * ");
        productBinary.getRight().accept(this);
        return productBinary;
    }

    @Override
    public ASTNode visit(MinusBinary minusBinary) {
        minusBinary.getLeft().accept(this);
        System.err.print(" - ");
        minusBinary.getRight().accept(this);
        return minusBinary;
    }

    @Override
    public ASTNode visit(DivideBinary divideBinary) {
        divideBinary.getLeft().accept(this);
        System.err.print(" / ");
        divideBinary.getRight().accept(this);
        return divideBinary;
    }

    @Override
    public ASTNode visit(EqBinary eqBinary) {
        eqBinary.getLeft().accept(this);
        System.err.print(" == ");
        eqBinary.getRight().accept(this);
        return eqBinary;
    }

    @Override
    public ASTNode visit(NEqBinary nEqBinary) {
        nEqBinary.getLeft().accept(this);
        System.err.print(" != ");
        nEqBinary.getRight().accept(this);
        return nEqBinary;
    }

    @Override
    public ASTNode visit(GTBinary gtBinary) {
        gtBinary.getLeft().accept(this);
        System.err.print(" > ");
        gtBinary.getRight().accept(this);
        return gtBinary;
    }

    @Override
    public ASTNode visit(GTEBinary gteBinary) {
        gteBinary.getLeft().accept(this);
        System.err.print(" >= ");
        gteBinary.getRight().accept(this);
        return gteBinary;
    }

    @Override
    public ASTNode visit(LTBinary ltBinary) {
        ltBinary.getLeft().accept(this);
        System.err.print(" < ");
        ltBinary.getRight().accept(this);
        return ltBinary;
    }

    @Override
    public ASTNode visit(LTEBinary lteBinary) {
        lteBinary.getLeft().accept(this);
        System.err.print(" <= ");
        lteBinary.getRight().accept(this);
        return lteBinary;
    }

    @Override
    public ASTNode visit(BangUnary bangUnary) {
        System.err.print("!");
        bangUnary.getExpression().accept(this);
        return bangUnary;
    }

    @Override
    public ASTNode visit(NegUnary negUnary) {
        System.err.print("-");
        negUnary.getExpression().accept(this);
        return negUnary;
    }

    @Override
    public ASTNode visit(BooleanLiteral booleanValue) {
        System.err.print(booleanValue);
        return booleanValue;
    }

    @Override
    public ASTNode visit(DecimalLiteral decimalValue) {
        System.err.print(decimalValue);
        return decimalValue;
    }

    @Override
    public ASTNode visit(IntegerLiteral integerValue) {
        System.err.print(integerValue);
        return integerValue;
    }

    @Override
    public ASTNode visit(StringLiteral stringValue) {
        System.err.print(stringValue);
        return stringValue;
    }

    @Override
    public ASTNode visit(IdentifierLiteral identifierValue) {
        System.err.print(identifierValue);
        return identifierValue;
    }

    @Override
    public ASTNode visit(DateLiteral dateLiteral) {
        return null;
    }

    @Override
    public ASTNode visit(MoneyLiteral moneyValue) {
        System.err.print(moneyValue);
        return moneyValue;
    }

    @Override public ASTNode visit(final QLIntegerType qlIntegerType) {
        return null;
    }

    @Override public ASTNode visit(final QLBooleanType qlBooleanType) {
        return null;
    }

    @Override public ASTNode visit(final QLDateType qlDateType) {
        return null;
    }

    @Override public ASTNode visit(final QLDecimalType qlDecimalType) {
        return null;
    }

    @Override public ASTNode visit(final QLMoneyType qlMoneyType) {
        return null;
    }

    @Override public ASTNode visit(final QLStringType qlStringType) {
        return null;
    }
}