package com.matthewchapman.ql.parsing;

import com.matthewchapman.antlr.QLBaseVisitor;
import com.matthewchapman.antlr.QLParser;
import com.matthewchapman.ql.ast.Expression;
import com.matthewchapman.ql.ast.Form;
import com.matthewchapman.ql.ast.Statement;
import com.matthewchapman.ql.ast.TreeNode;
import com.matthewchapman.ql.ast.atomic.Type;
import com.matthewchapman.ql.ast.atomic.type.BooleanType;
import com.matthewchapman.ql.ast.atomic.type.IntegerType;
import com.matthewchapman.ql.ast.atomic.type.StringType;
import com.matthewchapman.ql.ast.expression.Parameter;
import com.matthewchapman.ql.ast.expression.ParameterGroup;
import com.matthewchapman.ql.ast.expression.binary.*;
import com.matthewchapman.ql.ast.expression.literal.BooleanLiteral;
import com.matthewchapman.ql.ast.expression.literal.IntegerLiteral;
import com.matthewchapman.ql.ast.expression.literal.StringLiteral;
import com.matthewchapman.ql.ast.expression.unary.Negation;
import com.matthewchapman.ql.ast.statement.CalculatedQuestion;
import com.matthewchapman.ql.ast.statement.IfElseStatement;
import com.matthewchapman.ql.ast.statement.IfStatement;
import com.matthewchapman.ql.ast.statement.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 24/02/2017.
 */
public class ParseTreeVisitor extends QLBaseVisitor<TreeNode> {

    @Override
    public TreeNode visitFormDeclaration(QLParser.FormDeclarationContext ctx) {
        String id = ctx.ID().getText();
        List<Statement> statements = new ArrayList<>();

        for (QLParser.StatementContext statementContext : ctx.statement()) {
            statements.add((Statement) visit(statementContext));
        }

        return new Form(id, statements);
    }

    @Override
    public TreeNode visitQuestion(QLParser.QuestionContext ctx) {
        String questionID = ctx.ID().getText();
        String questionContent = ctx.STRING().getText().replaceAll("^\"|\"$", "");

        Type questionReturnType = (Type) visit(ctx.type());
        Expression calculation;

        if (ctx.calculatedValue() != null) {
            calculation = (Expression) visit(ctx.calculatedValue());
            return new CalculatedQuestion(questionID, questionContent, questionReturnType, calculation, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return new Question(questionID, questionContent, questionReturnType, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }

    @Override
    public TreeNode visitAddSub(QLParser.AddSubContext ctx) {
        Expression left = (Expression) visit(ctx.left);
        Expression right = (Expression) visit(ctx.right);

        if ("+".equals(ctx.op.getText())) {
            return new Addition(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return new Subtraction(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }

    @Override
    public TreeNode visitParameterGroup(QLParser.ParameterGroupContext ctx) {
        return new ParameterGroup((Expression) visit(ctx.expression()));
    }

    @Override
    public TreeNode visitMulDiv(QLParser.MulDivContext ctx) {
        Expression left = (Expression) visit(ctx.left);
        Expression right = (Expression) visit(ctx.right);

        if ("*".equals(ctx.op.getText())) {
            return new Multiplication(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return new Division(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }
    }

    @Override
    public TreeNode visitComparation(QLParser.ComparationContext ctx) {
        Expression left = (Expression) visit(ctx.left);
        Expression right = (Expression) visit(ctx.right);

        if ("<".equals(ctx.op.getText())) {
            return new LessThan(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (">".equals(ctx.op.getText())) {
            return new GreaterThan(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if ("<=".equals(ctx.op.getText())) {
            return new LessThanEqualTo(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if (">=".equals(ctx.op.getText())) {
            return new GreaterThanEqualTo(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else if ("!=".equals(ctx.op.getText())) {
            return new NotEqual(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        } else {
            return new Equal(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
        }

    }

    @Override
    public TreeNode visitIfStatement(QLParser.IfStatementContext ctx) {
        List<Statement> statements = new ArrayList<>();

        for (QLParser.StatementContext statementContext : ctx.statement()) {
            statements.add((Statement) visit(statementContext));
        }

        return new IfStatement((Expression) visit(ctx.expression()), statements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitIfElseStatement(QLParser.IfElseStatementContext ctx) {

        List<Statement> ifCaseStatements = new ArrayList<>();
        List<Statement> elseCaseStatements = new ArrayList<>();

        for (QLParser.StatementContext statementContext : ctx.ifCase) {
            ifCaseStatements.add((Statement) visit(statementContext));
        }

        for (QLParser.StatementContext statementContext : ctx.elseCase) {
            elseCaseStatements.add((Statement) visit(statementContext));
        }

        return new IfElseStatement((Expression) visit(ctx.expression()), ifCaseStatements, elseCaseStatements, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitStringLiteral(QLParser.StringLiteralContext ctx) {
        return new StringLiteral(ctx.STRING().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitParameter(QLParser.ParameterContext ctx) {
        return new Parameter(ctx.ID().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitLogicalAnd(QLParser.LogicalAndContext ctx) {
        Expression left = (Expression) visit(ctx.left);
        Expression right = (Expression) visit(ctx.right);
        return new LogicalAnd(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitLogicalOr(QLParser.LogicalOrContext ctx) {
        Expression left = (Expression) visit(ctx.left);
        Expression right = (Expression) visit(ctx.right);
        return new LogicalOr(left, right, ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitNegation(QLParser.NegationContext ctx) {
        return new Negation((Expression) visit(ctx.expression()), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitIntegerLiteral(QLParser.IntegerLiteralContext ctx) {
        return new IntegerLiteral(ctx.NUMBER().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitBooleanLiteral(QLParser.BooleanLiteralContext ctx) {
        return new BooleanLiteral(ctx.op.getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine());
    }

    @Override
    public TreeNode visitCalculatedValue(QLParser.CalculatedValueContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public TreeNode visitBooleanType(QLParser.BooleanTypeContext ctx) {
        return new BooleanType();
    }

    @Override
    public TreeNode visitIntegerType(QLParser.IntegerTypeContext ctx) {
        return new IntegerType();
    }

    @Override
    public TreeNode visitStringType(QLParser.StringTypeContext ctx) {
        return new StringType();
    }
}
