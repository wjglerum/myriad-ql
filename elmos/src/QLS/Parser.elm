module QLS.Parser exposing (parse)

import Combine exposing (InputStream, end, (<*))
import QLS.Parser.StyleSheet as StyleSheet
import QLS.AST exposing (StyleSheet)


parse : String -> Maybe StyleSheet
parse input =
    Combine.parse (StyleSheet.styleSheet <* end) input
        |> Result.toMaybe
        |> Maybe.map asStyleSheet


asStyleSheet : ( (), InputStream, StyleSheet ) -> StyleSheet
asStyleSheet ( _, _, sheetSheet ) =
    sheetSheet
