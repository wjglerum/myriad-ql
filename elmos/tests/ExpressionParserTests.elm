module ExpressionParserTests exposing (all)

import ExpressionParser
import Test exposing (Test, test, concat, describe)
import AST exposing (..)
import ParserTestUtil exposing (testWithParser)


all : Test
all =
    describe "ParserTests"
        [ atomTests
        , arithmeticTests
        , relationalTests
        , comparisonTests
        ]


atomTests : Test
atomTests =
    testWithParser ExpressionParser.expression
        "atomTests"
        [ ( "Should parse varName", "someVarName", Just (Var "someVarName") )
        , ( "Should parse int literal", "2", Just (Integer 2) )
        , ( "Should parse parens int literal", "(2)", Just (ParensExpression (Integer 2)) )
        ]


arithmeticTests : Test
arithmeticTests =
    testWithParser ExpressionParser.expression
        "arithmeticTests"
        [ ( "Should parse simple add", "2+3", Just (PlusExpression (Integer 2) (Integer 3)) )
        , ( "Should parse bigger add", "2+3+4", Just (PlusExpression (PlusExpression (Integer 2) (Integer 3)) (Integer 4)) )
        , ( "Should parse plus and multiplication", "2+3*4", Just (PlusExpression (Integer 2) (MultiplyExpression (Integer 3) (Integer 4))) )
        , ( "Should parse minus and division", "2-3/4", Just (MinusExpression (Integer 2) (DivideExpression (Integer 3) (Integer 4))) )
        , ( "Should parse variables", "x+y", Just (PlusExpression (Var "x") (Var "y")) )
        ]


relationalTests : Test
relationalTests =
    testWithParser ExpressionParser.expression
        "relationalTests"
        [ ( "Should parse less than relation", "x < y", Just (LessThanExpression (Var "x") (Var "y")) )
        , ( "Should parse greater than relation", "x > y", Just (GreaterThanExpression (Var "x") (Var "y")) )
        , ( "Should parse less than equal relation", "x <= y", Just (LessThanOrEqualExpression (Var "x") (Var "y")) )
        , ( "Should parse greater than equal relation", "x >= y", Just (GreaterThanOrEqualExpression (Var "x") (Var "y")) )
        , ( "Should parse relation with arithmetic"
          , "x+y < z * a"
          , Just
                (LessThanExpression
                    (PlusExpression (Var "x") (Var "y"))
                    (MultiplyExpression (Var "z") (Var "a"))
                )
          )
        ]


comparisonTests : Test
comparisonTests =
    testWithParser ExpressionParser.expression
        "comparisonTests"
        [ ( "Should parse equal comparison", "x == y", Just (EqualToExpression (Var "x") (Var "y")) )
        , ( "Should parse not equal comparison", "x != y", Just (NotEqualToExpression (Var "x") (Var "y")) )
        , ( "Should parse comparison with correct precedence"
          , "x + y == y < z"
          , Just
                (EqualToExpression
                    (PlusExpression (Var "x") (Var "y"))
                    (LessThanExpression (Var "y") (Var "z"))
                )
          )
        ]
