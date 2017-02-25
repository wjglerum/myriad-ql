package ui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Question;
import ast.Statement;
import ast.Visitor;
import value.Value;

// Or Statement Visitor
public class QuestionnaireVisitor extends Visitor {
	
	private List<QuestionnaireQuestion> activeQuestions; // TODO QQuestion String and type?
	private Map<String, Value> answers;
	
	public QuestionnaireVisitor(Map<String, Value> answers) {
		this.activeQuestions = new ArrayList<QuestionnaireQuestion>();
		this.answers = answers;
	}
	
	@Override 
	public void visit(Question question) {
        activeQuestions.add(new QuestionnaireQuestion(question.getVariable(),
        		question.getLabel(), question.getType()));
	}
	
	@Override
	public void visit(Statement statement) {	
		
		// TODO many functions - functions : can you assume the ATOM is a boolean?
		System.out.println("QUESTIONNAIRE VISITOR, value:");
		System.out.println(statement.getExpression().evaluate().getValue());

		// Call the evaluator with answers
		if (answers.size() != 0) {
			List<Value> valuesList = new ArrayList<Value>(answers.values());
			List<String> keysList = new ArrayList<String>(answers.keySet());

			Value answer = valuesList.get(0);
			String question = keysList.get(0);
			System.out.println("question: " + question);
			System.out.println("answer: " + answer);
			System.out.println("getvalue: " + statement.getExpression().evaluate().getValue());
		}
		if (statement.getExpression().evaluate().getValue()) {
			statement.getBlock().accept(this);
		}
	}

	public List<QuestionnaireQuestion> getActiveQuestions() {
		return activeQuestions;
	}	
}