package org.ql.evaluator;

import org.junit.Test;
import org.ql.ast.form.Form;
import org.ql.ast.identifier.Identifier;
import org.ql.ast.identifier.IdentifierSet;
import org.ql.ast.statement.Statement;
import org.ql.ast.expression.Parameter;
import org.ql.ast.expression.arithmetic.Addition;
import org.ql.ast.expression.literal.BooleanLiteral;
import org.ql.ast.expression.literal.IntegerLiteral;
import org.ql.ast.statement.ComputableQuestion;
import org.ql.ast.statement.IfThenElse;
import org.ql.ast.statement.Question;
import org.ql.ast.statement.question.QuestionLabel;
import org.ql.ast.type.IntegerType;
import org.ql.evaluator.value.IntegerValue;
import org.ql.evaluator.value.UnknownValue;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuestionEvaluatorTest {

    @Test
    public void shouldMakeValueTableWithFlatQuestionsAndNoReferences() {
        ValueTable expectedValueTable = new ValueTable();
        expectedValueTable.declare(new Identifier("first"), new IntegerValue(12));
        expectedValueTable.declare(new Identifier("second"), new IntegerValue(15));

        QuestionEvaluator visitor = new QuestionEvaluator(new IdentifierSet());
        ValueTable actualValueTable = new ValueTable();
        visitor.updateValueTable(new Form(new Identifier("Example"), new ArrayList<Statement>() {{
            add(new ComputableQuestion(new Identifier("first"), new QuestionLabel("ComputableQuestion"), new IntegerType(), new IntegerLiteral(12)));
            add(new ComputableQuestion(new Identifier("second"), new QuestionLabel("Question2"), new IntegerType(), new IntegerLiteral(15)));
        }}), actualValueTable);

        assertTrue(expectedValueTable.equals(actualValueTable));
    }

    @Test
    public void shouldMakeValueTableWithFlatQuestionsAndEmptyValues() {
        ValueTable expectedValueTable = new ValueTable();
        expectedValueTable.declare(new Identifier("first"), new IntegerValue(12));
        expectedValueTable.declare(new Identifier("second"), new UnknownValue());

        QuestionEvaluator visitor = new QuestionEvaluator(new IdentifierSet());
        ValueTable actualValueTable = new ValueTable();
        visitor.updateValueTable(new Form(new Identifier("Example"), new ArrayList<Statement>() {{
            add(new ComputableQuestion(new Identifier("first"), new QuestionLabel("ComputableQuestion"), new IntegerType(), new IntegerLiteral(12)));
            add(new Question(new Identifier("second"), new QuestionLabel("Question2"), new IntegerType()));
        }}), actualValueTable);

        assertTrue(expectedValueTable.equals(actualValueTable));
    }

    @Test
    public void shouldMakeValueTableWithFlatQuestionsAndReferences() {
        ValueTable expectedValueTable = new ValueTable();
        expectedValueTable.declare(new Identifier("first"), new IntegerValue(12));
        expectedValueTable.declare(new Identifier("second"), new IntegerValue(48));
        expectedValueTable.declare(new Identifier("third"), new IntegerValue(48));

        QuestionEvaluator visitor = new QuestionEvaluator(new IdentifierSet());
        ValueTable actualValueTable = new ValueTable();
        visitor.updateValueTable(new Form(new Identifier("Example"), new ArrayList<Statement>() {{
            add(new ComputableQuestion(new Identifier("first"), new QuestionLabel("ComputableQuestion"), new IntegerType(), new IntegerLiteral(12)));
            add(new ComputableQuestion(new Identifier("second"), new QuestionLabel("Question2"), new IntegerType(), new Parameter(new Identifier("third"))));
            add(new ComputableQuestion(new Identifier("third"), new QuestionLabel("Question2"), new IntegerType(), new Addition(
                    new IntegerLiteral(13), new IntegerLiteral(35)
            )));
        }}), actualValueTable);

        assertTrue(expectedValueTable.equals(actualValueTable));
    }

    @Test
    public void shouldMakeValueTableWithBranchedQuestions() {
        ValueTable expectedValueTable = new ValueTable();
        expectedValueTable.declare(new Identifier("first"), new IntegerValue(12));
        expectedValueTable.declare(new Identifier("second"), new IntegerValue(48));
        expectedValueTable.declare(new Identifier("third"), new IntegerValue(48));

        QuestionEvaluator visitor = new QuestionEvaluator(new IdentifierSet());
        ValueTable actualValueTable = new ValueTable();
        visitor.updateValueTable(new Form(new Identifier("Example"), new ArrayList<Statement>() {{
            add(new IfThenElse(new BooleanLiteral(true), new ArrayList<Statement>() {{
                add(new ComputableQuestion(new Identifier("first"), new QuestionLabel("ComputableQuestion"), new IntegerType(), new IntegerLiteral(12)));
            }}, new ArrayList<Statement>() {{
                add(new ComputableQuestion(new Identifier("second"), new QuestionLabel("Question2"), new IntegerType(), new Parameter(new Identifier("third"))));
                add(new ComputableQuestion(new Identifier("third"), new QuestionLabel("Question2"), new IntegerType(), new Addition(
                        new IntegerLiteral(13), new IntegerLiteral(35)
                )));
            }}));
        }}), actualValueTable);

        assertTrue(expectedValueTable.equals(actualValueTable));
    }
}
