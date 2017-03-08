package test.org.uva.taxfree.ast;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.uva.taxfree.ast.AstBuilder;
import org.uva.taxfree.main.SemanticsAnalyzer;
import org.uva.taxfree.model.environment.SymbolTable;
import org.uva.taxfree.model.node.blocks.BlockNode;

import java.io.File;
import java.io.IOException;

public class SemanticsAnalyzerTest {
    @Test
    public void testHasDuplicateQuestionLabels() throws Exception {
        assertSemantics("duplicateQuestionLabelForm.txt", 1, "Duplicate question label expected");
    }

    @Test
    public void testHasMultipleDuplicateQuestionLabels() throws Exception {
        assertSemantics("duplicateQuestionLabelsForm.txt", 2, "Duplicate question labels expected");
    }

    @Test
    public void testHasDuplicateDeclarations() throws Exception {
        assertSemantics("duplicateQuestionIdForm.txt", 1, "Duplicate question id expected");
    }

    @Test
    public void testHasMultipleDuplicateQuestionIds() throws Exception {
        assertSemantics("duplicateQuestionIdsForm.txt", 2, "Duplicate question id");
    }

    @Test
    public void testHasDuplicateQuestionIdsAndLabels() throws Exception {
        assertSemantics("duplicateQuestionIdsAndLabelsForm.txt", 4, "Duplicated questions and labels");
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
    void testCyclicDependency() throws Exception {
        assertSemantics("cyclicDependencyCalculations.txt", 2, "Cyclic dependency in calculation");
    }

    private void assertSemantics(String fileName, int expectedErrorAmount, String description) throws IOException {
        boolean expectedValid = 0 == expectedErrorAmount;
        SemanticsAnalyzer semanticsAnalyzer = createAnalyzer(fileName);
        Assert.assertEquals(semanticsAnalyzer.validSemantics(), expectedValid, "Expecting errors: " + description);
        Assert.assertEquals(semanticsAnalyzer.getSemanticErrors().size(), expectedErrorAmount, "Invalid error amount");
    }

    private SemanticsAnalyzer createAnalyzer(String fileName) throws IOException {
        AstBuilder builder = new AstBuilder(testFile(fileName));
        BlockNode ast = builder.generateTree();
        SymbolTable symbolTable = new SymbolTable();
        ast.fillSymbolTable(symbolTable);
        return new SemanticsAnalyzer(ast, symbolTable);
    }

    private File testFile(String fileName) {
        return new File("src\\test\\org\\uva\\taxfree\\ast\\semanticErrors\\" + fileName);
    }

}