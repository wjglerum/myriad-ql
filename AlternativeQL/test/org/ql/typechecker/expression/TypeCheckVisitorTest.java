package org.ql.typechecker.expression;

import org.junit.Test;
import org.ql.ast.expression.Visitor;
import org.ql.ast.Identifier;
import org.ql.ast.expression.Parameter;
import org.ql.ast.expression.arithmetic.Addition;
import org.ql.ast.expression.arithmetic.Decrement;
import org.ql.ast.expression.arithmetic.Division;
import org.ql.ast.expression.arithmetic.Increment;
import org.ql.ast.expression.literal.BooleanLiteral;
import org.ql.ast.expression.literal.DecimalLiteral;
import org.ql.ast.expression.literal.IntegerLiteral;
import org.ql.ast.expression.literal.StringLiteral;
import org.ql.ast.expression.relational.*;
import org.ql.ast.type.*;
import org.ql.typechecker.exception.TypeMismatchException;
import org.ql.typechecker.exception.UndefinedIdentifierException;
import org.ql.typechecker.exception.UnexpectedTypeException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TypeCheckVisitorTest {
    @Test(expected = TypeMismatchException.class)
    public void shouldThrowExceptionWhenNegationAppliedOnNonBoolean() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Negation negation = new Negation(new StringLiteral("example string"));

        visitor.visit(negation);
    }

    @Test
    public void shouldReturnBooleanTypeWhenNegationHasABooleanLiteral() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Negation negation = new Negation(new BooleanLiteral(true));

        Type actualNegationType = visitor.visit(negation);

        assertTrue(actualNegationType instanceof BooleanType);
    }

    @Test
    public void shouldReturnLiteralTypes() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());

        Type actualStringType = visitor.visit(new StringLiteral("example"));
        Type actualFloatType = visitor.visit(new DecimalLiteral(new BigDecimal(4.5)));
        Type actualBooleanType = visitor.visit(new BooleanLiteral(false));
        Type actualIntegerType = visitor.visit(new IntegerLiteral(34));

        assertTrue(actualStringType instanceof StringType);
        assertTrue(actualFloatType instanceof FloatType);
        assertTrue(actualBooleanType instanceof BooleanType);
        assertTrue(actualIntegerType instanceof IntegerType);
    }

    @Test
    public void shouldNotThrowTypeMismatchErrorWhenIncrementAppliedOnInteger() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());

        Type actualIntegerType = visitor.visit(new Increment(new IntegerLiteral(3)));

        assertTrue(actualIntegerType instanceof IntegerType);
    }

    @Test
    public void shouldReturnFloatTypeOnIncrementAppliedOnFloat() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());

        Type actualFloatType = visitor.visit(new Increment(new DecimalLiteral(new BigDecimal(10.40))));

        assertTrue(actualFloatType instanceof FloatType);
    }

    @Test(expected = UnexpectedTypeException.class)
    public void shouldThrowUnexpectedTypeExceptionWhenIncrementAppliedOnNonIntegerOrFloat() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());

        visitor.visit(new Increment(new StringLiteral("example")));
    }

    @Test
    public void shouldNotThrowTypeMismatchErrorWhenDecrementAppliedOnInteger() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());

        Type actualIntegerType = visitor.visit(new Decrement(new IntegerLiteral(3)));

        assertTrue(actualIntegerType instanceof IntegerType);
    }

    @Test
    public void shouldNotThrowTypeMismatchErrorWhenDecrementAppliedOnFloat() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());
        Type actualFloatType = visitor.visit(new Decrement(new DecimalLiteral(new BigDecimal(10.40))));
        assertTrue(actualFloatType instanceof FloatType);
    }

    @Test(expected = UnexpectedTypeException.class)
    public void shouldThrowTypeMismatchErrorWhenDecrementAppliedOnNonIntegerOrFloat() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());
        Type actualType = visitor.visit(new Decrement(new StringLiteral("example")));
        assertTrue(actualType instanceof IntegerType);
    }

    @Test(expected = UndefinedIdentifierException.class)
    public void shouldThrowUndefinedIdentifierExceptionWhenIdentifierDoesNotExist() throws Throwable {
        TypeCheckVisitor visitor = new TypeCheckVisitor(new HashMap<>());
        visitor.visit(new Parameter(new Identifier("example")));
    }

    @Test
    public void shouldReturnParameterWhenIdentifierExists() throws Throwable {
        Map<Identifier, Type> definitions = new HashMap<>();
        definitions.put(new Identifier("example"), new StringType());
        TypeCheckVisitor visitor = new TypeCheckVisitor(definitions);
        visitor.visit(new Parameter(new Identifier("example")));
    }

    @Test
    public void shouldReturnBooleanTypeForLogicalOr() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LogicalOr actualLogicalOr = new LogicalOr(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualLogicalOrType = visitor.visit(actualLogicalOr);

        assertTrue(actualLogicalOrType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInLogicalOr() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LogicalOr actualLogicalOr = new LogicalOr(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualLogicalOr);
    }

    @Test
    public void shouldReturnBooleanTypeForLowerThanOrEquals() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LowerThanOrEqual actualLowerThanOrEqual = new LowerThanOrEqual(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualLowerThanOrEqualType = visitor.visit(actualLowerThanOrEqual);

        assertTrue(actualLowerThanOrEqualType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInLowerThanOrEqual() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LowerThanOrEqual actualLowerThanOrEqual = new LowerThanOrEqual(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualLowerThanOrEqual);
    }

    @Test
    public void shouldReturnBooleanTypeForEquals() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Equals actualEqual = new Equals(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualEqualType = visitor.visit(actualEqual);

        assertTrue(actualEqualType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInEquals() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Equals actualEquals = new Equals(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualEquals);
    }

    @Test
    public void shouldReturnBooleanTypeForGreaterThan() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        GreaterThan actualGreaterThan = new GreaterThan(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualGreaterThanType = visitor.visit(actualGreaterThan);

        assertTrue(actualGreaterThanType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInGreaterThan() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        GreaterThan actualGreaterThan = new GreaterThan(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualGreaterThan);
    }

    @Test
    public void shouldReturnIntegerTypeForAddition() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Addition actualAddition = new Addition(new IntegerLiteral(123), new IntegerLiteral(321));

        Type actualAdditionType = visitor.visit(actualAddition);

        assertTrue(actualAdditionType instanceof IntegerType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInAddition() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Addition actualAddition = new Addition(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualAddition);
    }

    @Test
    public void shouldReturnIntegerTypeForDivision() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Division actualDivision = new Division(new IntegerLiteral(123), new IntegerLiteral(321));

        Type actualDivisionType = visitor.visit(actualDivision);

        assertTrue(actualDivisionType instanceof IntegerType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInDivision() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        Division actualDivision = new Division(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualDivision);
    }

    @Test
    public void shouldReturnBooleanTypeForGreaterThanOrEqual() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        GreaterThanOrEqual actualGreaterThanOrEqual = new GreaterThanOrEqual(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualGreaterThanOrEqualType = visitor.visit(actualGreaterThanOrEqual);

        assertTrue(actualGreaterThanOrEqualType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInGreaterThanOrEqual() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        GreaterThanOrEqual actualGreaterThanOrEqual = new GreaterThanOrEqual(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualGreaterThanOrEqual);
    }

    @Test
    public void shouldReturnBooleanTypeForLowerThan() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LowerThan actualLowerThan = new LowerThan(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualLowerThanType = visitor.visit(actualLowerThan);

        assertTrue(actualLowerThanType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInLowerThan() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LowerThan actualLowerThan = new LowerThan(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualLowerThan);
    }

    @Test
    public void shouldReturnBooleanTypeForLogicalAnd() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LogicalAnd actualLogicalAnd = new LogicalAnd(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualLogicalAndType = visitor.visit(actualLogicalAnd);

        assertTrue(actualLogicalAndType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInLogicalAnd() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        LogicalAnd actualLogicalAnd = new LogicalAnd(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualLogicalAnd);
    }

    @Test
    public void shouldReturnBooleanTypeForNotEqual() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        NotEqual actualNotEqual = new NotEqual(new BooleanLiteral(true), new BooleanLiteral(false));

        Type actualNotEqualType = visitor.visit(actualNotEqual);

        assertTrue(actualNotEqualType instanceof BooleanType);
    }

    @Test(expected = TypeMismatchException.class)
    public void shouldThrowTypeMismatchExceptionWhenDifferentTypesAreUsedInNotEqual() throws Throwable {
        Visitor<Type> visitor = new TypeCheckVisitor(new HashMap<>());
        NotEqual actualNotEqual = new NotEqual(new BooleanLiteral(true), new IntegerLiteral(12));

        visitor.visit(actualNotEqual);
    }
}
