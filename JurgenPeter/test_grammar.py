from unittest import TestCase, main
from grammar import *

from expression_ast import *
from form_ast import *


class TestGrammar(TestCase):

    correctSentences = [
        (Grammar.expression, "3"),
        (Grammar.expression, "+3"),
        (Grammar.expression, "-3"),
        (Grammar.expression, "3.0"),
        (Grammar.expression, "3."),
        (Grammar.expression, "\"three\""),
        (Grammar.expression, "true"),
        (Grammar.expression, "false"),
        (Grammar.expression, "!true"),
        (Grammar.expression, "x"),
        (Grammar.expression, "hasBoughtHouse"),

        (Grammar.expression, "2 + + 2"),
        (Grammar.expression, "x <= 3"),
        (Grammar.expression, "1 / 0"),
        (Grammar.expression, "2 == 2.0"),
        (Grammar.expression, "x * 3"),
        (Grammar.expression, "x * 3 + 5"),
        (Grammar.expression, "x * (3 + 5)"),
        (Grammar.expression, "x && true"),
        (Grammar.expression, "false || 3 > 2 && true"),
        (Grammar.expression, "x == 3 != false"),
        (Grammar.expression, "3 != 3.0"),
        (Grammar.expression, "2 < 16"),
        (Grammar.expression, "10. > 4 && hasBoughtHouse"),

        (Grammar.question, "x : \"y\" integer"),
        (Grammar.question, "x : \"y\" decimal"),
        (Grammar.question, "x : \"y\" boolean"),
        (Grammar.question, "x : \"y\" money"),
        (Grammar.question, "x : \"y\" string"),
        (Grammar.question, "x : \"y\" integer = 2 + 3"),
        (Grammar.question, "x : \"y\" string = \"hello world\""),
        (Grammar.conditional, "if true { }"),
        (Grammar.conditional, "if true { } else { }"),
        (Grammar.form, "form FormName { }")
    ]

    def testCorrectSentences(self):
        for grammar, sentence in self.correctSentences:
            self.assertTrue(grammar.parseString(sentence, parseAll=True))

    incorrectSentences = [
        (Grammar.expression, "x * * 3"),
        (Grammar.expression, "x * 3 *"),
        (Grammar.expression, "f(x)"),
        (Grammar.expression, "x + form"),
        (Grammar.expression, "x + if"),
        (Grammar.expression, "x + else"),
        (Grammar.question, "x : y integer"),
        (Grammar.question, "\"x\" : \"y\" integer"),
        (Grammar.question, "x : \"y\" float"),
        (Grammar.question, "x : \"y\" integer == 3"),
        (Grammar.question, "x : \"y\" integer ="),
        (Grammar.question, "x : \"y\" integer = 3 * * 3"),
        (Grammar.conditional, "if true then { }"),
        (Grammar.conditional, "if true { } else false { }"),
        (Grammar.form, "form { }"),
    ]

    def testIncorrectSentences(self):
        for grammar, sentence in self.incorrectSentences:
            self.assertRaises(ParseException,
                              lambda grammar, sentence: grammar.parseString(
                                  sentence, parseAll=True), grammar, sentence)

    expectedParseResults = [
        (Grammar.expression,
         "-2", MinOp(Constant(2, Datatype.integer))),
        (Grammar.expression,
         "+x", PlusOp(Variable("x"))),

        (Grammar.expression,
         "2 + 3", AddOp(Constant(2, Datatype.integer),
                        Constant(3, Datatype.integer))),
        (Grammar.expression,
         "+2 + -3", AddOp(PlusOp(Constant(2, Datatype.integer)),
                          MinOp(Constant(3, Datatype.integer)))),
        (Grammar.expression,
         "2 + 3 + 4", AddOp(AddOp(Constant(2, Datatype.integer),
                                  Constant(3, Datatype.integer)),
                            Constant(4, Datatype.integer))),

        (Grammar.expression,
         "2 + 3 - 4", SubOp(AddOp(Constant(2, Datatype.integer),
                                  Constant(3, Datatype.integer)),
                            Constant(4, Datatype.integer))),
        (Grammar.expression,
         "2 * 3 + 4", AddOp(MulOp(Constant(2, Datatype.integer),
                                  Constant(3, Datatype.integer)),
                            Constant(4, Datatype.integer))),
        (Grammar.expression,
         "2 + 3 * 4", AddOp(Constant(2, Datatype.integer),
                            MulOp(Constant(3, Datatype.integer),
                                  Constant(4, Datatype.integer)))),
        (Grammar.expression,
         "(2 + 3) * 4", MulOp(AddOp(Constant(2, Datatype.integer),
                                    Constant(3, Datatype.integer)),
                              Constant(4, Datatype.integer))),
        (Grammar.expression,
         "1 / (2 / 4)", DivOp(Constant(1, Datatype.integer),
                              DivOp(Constant(2, Datatype.integer),
                                    Constant(4, Datatype.integer)))),
        (Grammar.expression,
         "(1 / 2) / 4", DivOp(DivOp(Constant(1, Datatype.integer),
                                    Constant(2, Datatype.integer)),
                              Constant(4, Datatype.integer))),
        (Grammar.expression,
         "x <= 3", LeOp(Variable("x"),
                        Constant(3, Datatype.integer))),
        (Grammar.expression,
         "x * 3", MulOp(Variable("x"),
                        Constant(3, Datatype.integer))),
        (Grammar.expression,
         "x + 4 / y", AddOp(Variable("x"),
                            DivOp(Constant(4, Datatype.integer),
                                  Variable("y")))),
        (Grammar.expression,
         "x || true", OrOp(Variable("x"),
                           Constant(True, Datatype.boolean))),
        (Grammar.expression,
         "false == true", EqOp(Constant(False, Datatype.boolean),
                               Constant(True, Datatype.boolean))),
        (Grammar.expression,
         "true && !false", AndOp(Constant(True, Datatype.boolean),
                                 NotOp(Constant(False, Datatype.boolean)))),

        (Grammar.question,
         "x : \"y\" integer", Question("x",
                                       "y",
                                       Datatype.integer)),
        (Grammar.question,
         "x : \"y\" integer = 2 + 3",
         Question("x",
                  "y",
                  Datatype.integer,
                  AddOp(Constant(2, Datatype.integer),
                        Constant(3, Datatype.integer)))),

        (Grammar.conditional,
         "if true { x : \"y\" integer }",
         Conditional(Constant(True, Datatype.boolean),
                     [Question("x",
                               "y",
                               Datatype.integer)])),
        (Grammar.conditional,
         "if true { }", Conditional(Constant(True, Datatype.boolean), [])),
        (Grammar.conditional,
         "if true { } else { }", Conditional(Constant(True, Datatype.boolean),
                                             [],
                                             [])),

        (Grammar.form, "form FormName { }", Form("FormName", [])),

        (Grammar.form,
         """
         form FormName {
            x: \"xLabel\" integer = 2 * 3
            if x > 6 {
                y: \"yLabel\" boolean = true
            }
            else {
                y: \"yLabel\" boolean = false
            }
        }""",
         Form("FormName",
              [Question("x",
                        "xLabel",
                        Datatype.integer,
                        MulOp(Constant(2, Datatype.integer),
                              Constant(3, Datatype.integer))),
               Conditional(GtOp(Variable("x"),
                                Constant(6, Datatype.integer)),
                           [Question("y",
                                     "yLabel",
                                     Datatype.boolean,
                                     Constant(True, Datatype.boolean))],
                           [Question("y",
                                     "yLabel",
                                     Datatype.boolean,
                                     Constant(False, Datatype.boolean))])])),
    ]

    def testParsing(self):
        for grammar, sentence, expected in self.expectedParseResults:
            parse_result = grammar.parseString(sentence, parseAll=True)[0]
            self.assertEqual(parse_result, expected)

if __name__ == "__main__":
    main()
