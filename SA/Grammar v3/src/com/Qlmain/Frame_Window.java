package com.Qlmain;

import com.Qlmain.Frame_Listeners.Checkbox_Listener;
import com.Qlmain.Frame_Listeners.NumberField_Listener;
import com.Qlmain.Frame_Listeners.StringField_Listener;
import com.Qlmain.QL.*;
import com.Qlmain.Types_Of_Expr.Expression;
import com.Qlmain.Types_Of_Expr.Number_ops.giveValEqual;
import com.Qlmain.Types_Of_Expr.Type;
import com.Qlmain.type_check.Type_Checking;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class Frame_Window {

    private static Map<String, Object> variablesAndValues;
    private static Map<IfStatement, JPanel> panelsAndConditions;
    private static Map<Expression, JTextField> textFieldWithExprToEval;

    public void Custom_Frame(Form dataToDisplay) {
        variablesAndValues = new HashMap<>();
        panelsAndConditions = new HashMap<>();
        textFieldWithExprToEval = new HashMap<>();
        JFrame frame = new JFrame("Check Box Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = new Dimension(500,500);
        frame.setPreferredSize(dim);
        frame.getContentPane().add( questionsToDisplay(dataToDisplay.getStatementList()));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JPanel questionsToDisplay(List<Statement> dataToDisplay){
        JPanel newItemPanel = new JPanel();
        newItemPanel.setLayout(new BoxLayout(newItemPanel,BoxLayout.Y_AXIS));
        newItemPanel.setBackground(Color.WHITE);

        for (Statement statementItem : dataToDisplay) {

            if (statementItem instanceof Question) {

                Question questionItem = (Question) statementItem;
                JLabel jlabel = new JLabel(questionItem.text);
                jlabel.setForeground(Color.blue);
                Font font = new Font("Verdana", Font.ITALIC, 12);
                jlabel.setFont(font);

                newItemPanel = defineQuestionType(questionItem, newItemPanel, dataToDisplay, jlabel);

            }else if (statementItem instanceof IfStatement) {

                JPanel tempPan = questionsToDisplay( ((IfStatement) statementItem).getStatementsList());
                panelsAndConditions.put((IfStatement) statementItem, tempPan );
                newItemPanel.add(tempPan);

                if (!(boolean) ((IfStatement) statementItem).getIfCase().exprEvaluateVisitor() ) {
                    tempPan.setVisible(false);
                }else {
                    tempPan.setVisible(true);
                }
            }

        }
        return newItemPanel;
    }

    private static JPanel defineQuestionType(Question questionItem, JPanel newItemPanel, List<Statement> dataToDisplay, JLabel jlabel) {
        Map<String,Type> variablesAndTypes = Type_Checking.getVariablesAndTypes();

        JPanel temppanel = new JPanel(new GridLayout(1,1));
        Dimension dim = new Dimension(500,30);
        temppanel.setMaximumSize(dim);
        temppanel.setBackground(Color.WHITE);

        temppanel.add(jlabel);

        if (variablesAndTypes.get(questionItem.name) == Type.BOOLEAN ) {

            variablesAndValues.put(questionItem.name, false);
            JCheckBox questionCheckBox = new JCheckBox();
            questionCheckBox.setBackground(Color.WHITE);
            questionCheckBox.addActionListener(new Checkbox_Listener(questionItem,dataToDisplay));
            temppanel.add(questionCheckBox);

        }else if (variablesAndTypes.get(questionItem.name) == Type.STRING ){

            variablesAndValues.put(questionItem.name, 0);
            JTextField questionTextField = new JTextField();
            questionTextField.getDocument().addDocumentListener(new StringField_Listener(questionItem, questionTextField));
            questionTextField.setBackground(Color.WHITE);
            temppanel.add(questionTextField);

        }else if (variablesAndTypes.get(questionItem.name) == Type.INTEGER ||
                variablesAndTypes.get(questionItem.name) == Type.MONEY) {

            JTextField questionTextField = new JTextField();
            if (questionItem.type instanceof giveValEqual) {
                questionTextField.setEditable(false);
                variablesAndValues.put(questionItem.name, questionItem.type.exprEvaluateVisitor());
                questionTextField.setText(questionItem.type.exprEvaluateVisitor().toString());
                textFieldWithExprToEval.put(questionItem.type, questionTextField);
            }else{
                if (variablesAndTypes.get(questionItem.name) == Type.INTEGER){
                    variablesAndValues.put(questionItem.name, 0);
                }else{
                    variablesAndValues.put(questionItem.name, 0.0);
                }
                questionTextField.getDocument().addDocumentListener(new NumberField_Listener(questionItem, questionTextField));
            }
            questionTextField.setBackground(Color.WHITE);

            temppanel.add(questionTextField);
        }
        newItemPanel.add(temppanel);
        return newItemPanel;
    }

    public static Map<String, Object> getVariablesAndValues() { return variablesAndValues; }
    public static Map<IfStatement, JPanel> getPanelsAndConditions() {
        return panelsAndConditions;
    }
    public static Map<Expression, JTextField> gettextFieldWithExprToEval() { return textFieldWithExprToEval; }
}
