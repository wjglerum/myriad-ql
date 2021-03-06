package org.qls.ast.widget.default_widget;

import org.junit.Test;
import org.ql.ast.type.BooleanType;
import org.qls.ast.widget.Checkbox;
import org.qls.ast.widget.default_widget.style.ColorRule;
import org.qls.ast.widget.default_widget.style.FontRule;
import org.qls.ast.widget.default_widget.style.StyleRule;

import java.util.HashSet;

import static org.junit.Assert.*;

public class DefaultWidgetWithStyleTest {
    @Test
    public void shouldPersistUniquenessOfStyleRulesWithinAWidget() {
        HashSet<StyleRule> styleRules = new HashSet<StyleRule>() {{
            add(new FontRule("Times New Roman"));
            add(new ColorRule("#000000"));
            add(new ColorRule("#FFFFFF"));
            add(new ColorRule("#333333"));
        }};

        DefaultWidgetWithStyle widget = new DefaultWidgetWithStyle(new BooleanType(), new Checkbox(), styleRules);

        ColorRule colorRule = (ColorRule) widget.getStyleRules().toArray()[0];
        FontRule fontRule = (FontRule) widget.getStyleRules().toArray()[1];

        assertSame(2, widget.getStyleRules().size());
        assertEquals("#000000", colorRule.getHexCode());
        assertEquals("Times New Roman", fontRule.getFont());
    }
}