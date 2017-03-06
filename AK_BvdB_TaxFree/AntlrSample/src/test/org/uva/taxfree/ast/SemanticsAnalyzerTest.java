package test.org.uva.taxfree.ast;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.uva.taxfree.ast.Ast;
import org.uva.taxfree.main.SemanticsAnalyzer;
import org.uva.taxfree.model.environment.Environment;

import java.io.File;
import java.io.IOException;

public class SemanticsAnalyzerTest {
    @Test
    public void testHasDuplicateQuestionLabels() throws Exception {
        Environment environment = Ast.generateAst(testFile("duplicateQuestionLabelForm.txt"));
        SemanticsAnalyzer semanticsAnalyzer = new SemanticsAnalyzer(environment);
        Assert.assertFalse(semanticsAnalyzer.validSemantics(), "Duplicate question label, so test should fail");
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), 1, "We only have one duplicate here");
    }

    @Test
    public void testHasMultipleDuplicateQuestionLabels() throws Exception {
        Environment environment = Ast.generateAst(testFile("duplicateQuestionLabelsForm.txt"));
        SemanticsAnalyzer semanticsAnalyzer = new SemanticsAnalyzer(environment);
        Assert.assertFalse(semanticsAnalyzer.validSemantics(), "Duplicate question label, so test should fail");
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), 2, "We should have three duplicates here");
    }

    @Test
    public void testHasDuplicateDeclarations() throws Exception {
        SemanticsAnalyzer semanticsAnalyzer = createAnalyzer("duplicateQuestionIdForm.txt");
        Assert.assertFalse(semanticsAnalyzer.validSemantics(), "Duplicate question id, so test should fail");
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), 1, "We only have one duplicate here");
    }

    @Test
    public void testHasMultipleDuplicateQuestionIds() throws Exception {
        SemanticsAnalyzer semanticsAnalyzer = createAnalyzer("duplicateQuestionIdsForm.txt");
        Assert.assertFalse(semanticsAnalyzer.validSemantics(), "Duplicate question id, so test should fail");
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), 2, "We should have three duplicates here");
    }

    @Test
    public void testHasDuplicateQuestionIdsAndLabels() throws Exception {
        SemanticsAnalyzer semanticsAnalyzer = createAnalyzer("duplicateQuestionIdsAndLabelsForm.txt");
        Assert.assertFalse(semanticsAnalyzer.validSemantics(), "Duplicate question id, so test should fail");
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), 4, "We should have four duplicates here");
    }

    @Test
    public void testUndefinedDeclarationSingle() throws Exception {
        assertSemantics("undefinedDeclarationSingle.txt", 1, "Undefined declaration should throw an error");
    }

    @Test
    public void testUndefinedDeclarationMultiple() throws Exception {
        assertSemantics("undefinedDeclarationMultiple.txt", 1, "Multiple conditions with same variable trigger 1 error");
    }

    @Test
    public void testUndefinedDeclarations() throws Exception {
        assertSemantics("undefinedDeclarations.txt", 11, "Undefined declarations");
    }

    @Test
    void testCyclicDependencyCalculation() throws Exception {
        assertSemantics("cyclicDependencyCalculation.txt", 1, "Cyclic dependency in calculation");
    }

    @Test
    void testCyclicDependencyCalculations() throws Exception {
        assertSemantics("cyclicDependencyCalculations.txt", 2, "Cyclic dependency in calculations");
    }

    @Test
    void testCyclicDependency() throws Exception {
        assertSemantics("invalidDelegatedCalculations.txt", 5, "Substitution of variables should not trigger errors");
    }

    @Test
    void testCyclicDependencyComposed() throws Exception {
        assertSemantics("invalidDelegatedComposedCalculations.txt", 5, "Composition should work");
    }

    @Test(timeOut = 2000)
    void testCyclicDependencyDelegated() throws Exception {
        assertSemantics("invalidDelegations.txt", 2, "Delegation leads to errors in any expression");
    }

    private void assertSemantics(String fileName, int expectedErrorAmount, String description) throws IOException {
        boolean expectedValid = 0 == expectedErrorAmount;
        SemanticsAnalyzer semanticsAnalyzer = createAnalyzer(fileName);
        Assert.assertEquals(semanticsAnalyzer.validSemantics(), expectedValid, "Expecting errors: " + description);
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), expectedErrorAmount, "Invalid error amount");
    }

    private SemanticsAnalyzer createAnalyzer(String fileName) throws IOException {
        Environment environment = Ast.generateAst(testFile(fileName));
        return new SemanticsAnalyzer(environment);
    }

    private File testFile(String fileName) {
        return new File("src\\test\\org\\uva\\taxfree\\ast\\semanticsCheckerTestInput\\" + fileName);
    }

}