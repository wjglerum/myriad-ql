from unittest import TestCase, main
from pyparsing import ParseException

import qls.grammar as grammar
from qls.ast import *
from gui.widgets import *


class TestGrammar(TestCase):

    correctSentences = [
        (grammar.question, "question x"),
        (grammar.question, "question x widget spinbox"),
        (grammar.question, "question x widget spinbox(-10, 10)"),
        (grammar.question, "question x {}"),
        (grammar.question, "question x {color: #aabbcc}"),
        (grammar.question, "question x {color: #aabbcc widget text}"),
        (grammar.default, "default integer widget spinbox"),
        (grammar.default, "default integer {color: #aabbcc widget text}"),
        (grammar.section, "section \"x\" {question y}"),
        (grammar.section, "section \"x\" {question y question z}"),
        (grammar.section, "section \"x\" {"
                          "     section \"y\" {"
                          "         question z"
                          "     }"
                          "     question zz"
                          "}"),
        (grammar.section, "section \"x\" {"
                          "     question y"
                          "     default string widget text"
                          "}"),
        (grammar.page, "page p {"
                       "    section \"x\" {"
                       "        section \"y\" {"
                       "            question z"
                       "        }"
                       "        question zz"
                       "    }"
                       "}"),
        (grammar.page, "page p {"
                       "    section \"x\" {question x}"
                       "    default string widget text"
                       "}"),
        (grammar.layout, "stylesheet s {page p {question x}"
                         "              page q {question y}}"),
        (grammar.layout, "stylesheet s {"
                         "  page p {question x}"
                         "  default integer widget slider"
                         "}"),
    ]

    def testCorrectSentences(self):
        for parser, sentence in self.correctSentences:
            self.assertTrue(parser.parseString(sentence, parseAll=True))

    incorrectSentences = [
        (grammar.question, "question x color #663322"),
        (grammar.question, "question x color: #663322"),
        (grammar.question, "question x widget: spinbox"),
        (grammar.question, "question x { widget: spinbox }"),
        (grammar.question, "question x widget radio(0, 1)"),
        (grammar.question, "question x widget dropdown(0, 1)"),
        (grammar.question, "question x widget: spinbox(\"0\",\"100\")"),
        (grammar.question, "question x widget: slider(\"0\",\"100\")"),
        (grammar.section, "section x {question x}"),
        (grammar.section, "section \"x\" {}"),
        (grammar.section, "section \"x\" {"
                          "     default boolean widget radio question x"
                          "}"),
        (grammar.page, "page \"x\" {question x}"),
        (grammar.page, "page x {}"),
        (grammar.page, "page x {default boolean widget radio question x}"),
        (grammar.layout, "stylesheet \"x\" {}"),
        (grammar.layout, "stylesheet x {"
                         "      default boolean widget radio question x"
                         "}"),
    ]

    def testIncorrectSentences(self):
        for parser, sentence in self.incorrectSentences:
            self.assertRaises(ParseException,
                              lambda parser, sentence: parser.parseString(
                                  sentence, parseAll=True), parser, sentence)

    expectedParseResults = [
        (grammar.styling, "{color: #663311"
                          " size: 16"
                          " weight: bold"
                          " family: \"Roboto\""
                          " width: 400"
                          " widget checkbox}",
         [ColorAttribute("#663311"),
          FontSizeAttribute(16),
          FontWeightAttribute("bold"),
          FontFamilyAttribute("Roboto"),
          WidthAttribute(400),
          WidgetTypeAttribute(CheckBoxWidget)]),
        (grammar.styling, "{}", []),
        (grammar.styling, "widget checkbox",
                          [WidgetTypeAttribute(CheckBoxWidget)]),
        (grammar.question, "question x",
                           QuestionAnchor("x")),
        (grammar.question, "question x {"
                           "    color: #663311"
                           "}",
                           StyledQuestionAnchor("x",
                                                [ColorAttribute("#663311")])),
        (grammar.question, "question x widget checkbox",
                           StyledQuestionAnchor("x",
                                                [WidgetTypeAttribute(
                                                    CheckBoxWidget)])),
        (grammar.default, "default boolean {"
                          "     color: #663311"
                          "}",
                          DefaultStyling(BooleanDatatype(),
                                         [ColorAttribute("#663311")])),
        (grammar.default, "default boolean widget checkbox",
                          DefaultStyling(BooleanDatatype(),
                                         [WidgetTypeAttribute(
                                             CheckBoxWidget)])),
        (grammar.section, "section \"x\" {"
                          "     question y"
                          "     question z"
                          "}", Section("x",
                                       [QuestionAnchor("y"),
                                        QuestionAnchor("z")])),
        (grammar.section, "section \"x\" {"
                          "     question y"
                          "     question z"
                          "     default boolean widget checkbox"
                          "     default integer widget whole-number"
                          "}",
            StyledSection("x",
                          [QuestionAnchor("y"),
                           QuestionAnchor("z")],
                          [DefaultStyling(BooleanDatatype(),
                                          [WidgetTypeAttribute(
                                              CheckBoxWidget)]),
                           DefaultStyling(IntegerDatatype(),
                                          [WidgetTypeAttribute(
                                             IntegerEntryWidget)])])),
        (grammar.section, "section \"x\" {"
                          "     section \"y\" {question x}"
                          "}",
                          Section("x",
                                  [Section("y",
                                           [QuestionAnchor("x")])])),
        (grammar.page, "page x {"
                       "    section \"y\" {question x}"
                       "}",
                       Page("x",
                            [Section("y",
                                     [QuestionAnchor("x")])])),
        (grammar.page, "page x {"
                       "    question y"
                       "    question z"
                       "}",
                       Page("x",
                            [QuestionAnchor("y"),
                             QuestionAnchor("z")])),
        (grammar.page, "page x {"
                       "    question y"
                       "    question z"
                       "    default boolean widget checkbox"
                       "    default integer widget whole-number"
                       "}",
                       StyledPage("x",
                                  [QuestionAnchor("y"),
                                   QuestionAnchor("z")],
                                  [DefaultStyling(BooleanDatatype(),
                                                  [WidgetTypeAttribute(
                                                      CheckBoxWidget)]),
                                   DefaultStyling(IntegerDatatype(),
                                                  [WidgetTypeAttribute(
                                                      IntegerEntryWidget)])])),
        (grammar.layout, "stylesheet x {"
                         "      page y {question x}"
                         "      page z {question y}"
                         "}",
                         Layout("x",
                                [Page("y", [QuestionAnchor("x")]),
                                 Page("z", [QuestionAnchor("y")])])),
        (grammar.layout, "stylesheet x {"
                         "      page y {question x}"
                         "      default decimal widget real-number"
                         "}",
                         StyledLayout("x",
                                      [Page("y", [QuestionAnchor("x")])],
                                      [DefaultStyling(DecimalDatatype(),
                                                      [WidgetTypeAttribute(
                                                          DecimalEntryWidget)
                                                       ])]))
    ]

    def testParsing(self):
        for parser, sentence, expected in self.expectedParseResults:
            parse_result = parser.parseString(sentence, parseAll=True)[0]
            self.assertEqual(parse_result, expected)


if __name__ == "__main__":
    main()
