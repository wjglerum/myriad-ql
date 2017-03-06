package org.ql.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ql.ast.Form;
import org.ql.evaluator.Evaluator;
import org.ql.evaluator.ValueTable;
import org.ql.parser.Parser;
import org.ql.typechecker.Messages;
import org.ql.typechecker.SymbolTable;
import org.ql.typechecker.TypeChecker;
import org.ql.typechecker.circular_dependencies.CircularDependenciesResolver;

public class GUIHandler extends Application {

    private MainStage mainStage;

    public static void main(String args[]) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parser parser = new Parser();
        Form form = parser.parseForm("form TestForm {" +
                "boolean hasSoldHouse: \"Did you sell a house in 2010?\";" +
                "}");

        Messages messages = new Messages();
        TypeChecker typeChecker = new TypeChecker(messages, new SymbolTable(), new CircularDependenciesResolver());
        typeChecker.visit(form, null);

        if(messages.hasErrors()) {
            System.out.println("An error was found!");
        } else {
            ValueTable valueTable = new ValueTable();
            Evaluator evaluator = new Evaluator(valueTable);
            evaluator.visit(form, null);

            primaryStage.show();
            MainStage mainStage = new MainStage(primaryStage);
            GUIVisitor guiVisitor = new GUIVisitor(mainStage, valueTable);
            guiVisitor.visit(form, null);
        }
    }
}
