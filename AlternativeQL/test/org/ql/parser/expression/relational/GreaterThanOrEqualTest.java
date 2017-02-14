package org.ql.parser.expression.relational;

import static org.junit.Assert.*;
import org.junit.Test;
import org.ql.parser.Parser;

public class GreaterThanOrEqualTest {
    @Test
    public void shouldParseGreaterThanOrEquals() {
        String inputCode = "5>=7";

        String actual = new Parser().parseExpression(inputCode).toString();

        assertEquals("(5>=7)", actual);
    }
}
