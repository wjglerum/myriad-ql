module AST exposing (..)


type alias Form =
    { name : String
    , items : List FormItem
    }


type FormItem
    = FieldItem Field
    | IfItem IfBlock


type alias Field =
    { label : String
    , id : String
    , valueType : ValueType
    , valueExpression : Maybe Expression
    }


type alias IfBlock =
    { expression : Expression
    , thenBranch : List FormItem
    , elseBranch : Maybe (List FormItem)
    }


type Expression
    = Var String
    | Integer Int
    | Boolean Bool
    | ParensExpression Expression
    | ArithmeticExpression Operator Expression Expression
    | GreaterThanExpression Expression Expression
    | LessThanExpression Expression Expression
    | GreaterThanOrEqualExpression Expression Expression
    | LessThanOrEqualExpression Expression Expression
    | EqualToExpression Expression Expression
    | NotEqualToExpression Expression Expression
    | AndExpression Expression Expression
    | OrExpression Expression Expression


type Operator
    = Plus
    | Minus
    | Divide
    | Multiply


type ValueType
    = StringType
    | BooleanType
    | IntegerType
