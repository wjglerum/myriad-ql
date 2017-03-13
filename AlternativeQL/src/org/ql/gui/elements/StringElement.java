package org.ql.gui.elements;

import org.ql.ast.Identifier;
import org.ql.evaluator.value.StringValue;
import org.ql.evaluator.value.Value;
import org.ql.gui.QuestionPane;
import org.ql.gui.mediator.GUIMediator;
import org.ql.gui.widgets.TextInputWidget;

public class StringElement extends Element {
    private final TextInputWidget widget;
    private boolean isDirty = false;

    public StringElement(GUIMediator mediator, Identifier id, TextInputWidget widget) {
        this.widget = widget;
        widget.addEventHandler(event -> {
            isDirty = true;
            mediator.actualizeValue(id, new StringValue(widget.getInputValue()));
        });
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public void updateValue(Value value) {
        widget.setInputValue((String) value.getPlainValue());
    }

    @Override
    public void attachToPane(QuestionPane pane) {
        pane.add(widget.createGridPane());
    }
}
