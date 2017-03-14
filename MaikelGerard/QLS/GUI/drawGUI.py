# -*- coding: utf-8 -*-
from QL.GUI.drawGUI import DrawGUI as QLDrawGUI
from appJar import gui
from collections import OrderedDict

from QL.undefined import Undefined
from QL.GUI.evaluateDrawState import EvaluateDrawState
from QL.GUI.saveQuestionaire import SaveQuestionaire
from QL.stages.updateComputedVars import UpdateComputedVars
import QLS.GUI.widgets as Widgets

"""
Quick rundown of what we probably need to do:
- Traverse QLS ast, to add formatting, frames etc.
- When at QLS questions, retrieve the QL question and call the
  parent(?) to create the appropriate GUI widget.
- After the widgets are created, use the parent's methods to update the
  GUI. What do we do when all Questions in a section are hidden? Hide the
  section as well? How are we going to do that?
"""


class DrawGUI(QLDrawGUI):
    def __init__(self, qls_ast, qls_env, ql_ast, ql_env):
        super(DrawGUI, self).__init__(ql_ast, ql_env)
        self.qls_ast = qls_ast
        self.qls_env = qls_env

    def start_traversal(self):
        self.ast.accept(self)

    def style_sheet_node(self, style_sheet_node):
        # TODO: Add stuff to self.main to create awesome design/styling.
        style_sheet_node.body.accept(self)

    def page_node(self, page_node):
        page_node.body.accept(self)

    def page_with_defaults_node(self, page_node):
        self.page_node(page_node)

    def section_node(self, section_node):
        section_node.body.accept(self)

    def section_with_defaults_node(self, section_node):
        self.section_node(section_node)

    def question_node(self, question_node):
        # Retrieve QL question type.
        ql_node = self.env.get_var_node(question_node.name)

        # TODO: Create node with appropriate widget? (Call super?)
        # TODO: Seems not to be possible; redefine creation of widgets here?
        # TODO: Determine here if the question has a QLS Default styling
        # attached; use WidgetNode to return correct Widget function?
        # TODO: If no styling, visit (parent's) question type function to
        # get the default Widget function.
        # TODO: Perhaps could still use parent.create_widget, when all
        # data is gathered.
        # TODO: Apply styling to the widget.

    def widget_question_node(self, question_node):
        # TODO: Here only apply styling, not QLS Default widget type, if
        # there is a QLS Default styling available.
        self.question_node(question_node)

    def slider_node(self, _):
        return Widgets.SliderWidget

    def spinbox_node(self, _):
        return Widgets.SpinBoxWidget

    def text_node(self, _):
        return Widgets.EntryWidget

    def numeric_node(self, _):
        return Widgets.NumericWidget

    def radio_node(self, _):
        return Widgets.RadioButtonWidget

    def checkbox_node(self, _):
        return Widgets.CheckBoxWidget

    def dropdown_node(self, _):
        return Widgets.DropDownWidget

    def width_node(self, width_node, widget):
        widget.set_width(width_node.val)

    def height_node(self, height_node, widget):
        widget.set_height(height_node.val)

    def font_node(self, font_node, widget):
        widget.set_font_family(font_node.val)

    def fontsize_node(self, fontsize_node, widget):
        widget.set_font_size(fontsize_node.val)

    def color_node(self, color_node, widget):
        widget.set_font_size(color_node.val)
