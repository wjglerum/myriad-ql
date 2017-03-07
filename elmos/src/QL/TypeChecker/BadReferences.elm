module QL.TypeChecker.BadReferences exposing (badReferences)

import QL.AST exposing (..)
import Dict
import Set exposing (Set)
import QL.AST.Collectors as Collectors
import QL.TypeChecker.Messages exposing (Message, referenceToUndefinedQuestion)
import QL.TypeChecker.QuestionIndex as QuestionIndex


badReferences : Form -> List Message
badReferences form =
    let
        ids =
            QuestionIndex.questionIndexFromForm form |> Dict.keys |> Set.fromList

        expressions =
            Collectors.collectExpressions form
    in
        List.concatMap (badReferencesInExpression ids) expressions


badReferencesInExpression : Set String -> Expression -> List Message
badReferencesInExpression availableIdentifiers expression =
    Collectors.collectQuestionReferences expression
        |> List.filter (flip isBadReference availableIdentifiers)
        |> List.map referenceToUndefinedQuestion


isBadReference : Id -> Set String -> Bool
isBadReference ( id, _ ) =
    Set.member id >> not
