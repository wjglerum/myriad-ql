package test.org.uva.taxfree.ql.semantics;

import org.testng.annotations.Test;
import test.org.uva.taxfree.ql.SemanticsTester;

import java.io.File;

public class SemanticsAnalyzerTest extends SemanticsTester {
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

    @Override
    protected File testFile(String fileName) {
        return new File("src\\test\\org\\uva\\taxfree\\ql\\ast\\semanticErrors\\" + fileName);
    }

}