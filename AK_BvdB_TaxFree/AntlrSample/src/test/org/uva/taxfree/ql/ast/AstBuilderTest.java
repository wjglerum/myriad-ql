package test.org.uva.taxfree.ql.ast;

import org.testng.TestException;
import org.testng.annotations.Test;
import org.uva.taxfree.ql.ast.AstBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AstBuilderTest {
    @Test
    public void testValidForms() throws Exception {
        File validFilesDir = new File("src\\test\\org\\uva\\taxfree\\ql\\testFiles\\validForms");

        System.out.println("  Testing valid forms at path: " + validFilesDir.getAbsolutePath());
        boolean passedAllTests = true;
        for (File file : validFilesDir.listFiles()) {
            System.out.println("    - Input file: " + file.getName());
            try {
                AstBuilder builder = new AstBuilder(file);
                builder.generateTree();
            } catch (UnsupportedOperationException e) {
//                e.printStackTrace();
                passedAllTests = false;
            }
        }
        if (passedAllTests) {
            System.out.println("  All tests successful!");
        } else {
            throw new TestException("Some tests failed!");
        }
    }

    @Test
    public void testInvalidForms() throws Exception {
        File validFilesDir = new File("src\\test\\org\\uva\\taxfree\\ql\\testFiles\\invalidForms");

        System.out.println("  Testing invalid forms at path: " + validFilesDir.getAbsolutePath());
        List<String> parseErrors = new ArrayList<>();
        for (File file : validFilesDir.listFiles()) {
            System.out.println("    - Input file: " + file.getName());
            try {
                AstBuilder builder = new AstBuilder(file);
                builder.generateTree();
                parseErrors.add("Invalid file should not not be parsed: " + file.getName());
            } catch (UnsupportedOperationException e) {
                // Invalid form successfully caught
            }

        }
        if (!parseErrors.isEmpty()) {
            System.out.println(parseErrors);
            throw new TestException("  Failed! Some invalid forms were parsed!:");
        } else {
            System.out.println("  Success! No invalid forms were silently parsed!");
        }
    }
}