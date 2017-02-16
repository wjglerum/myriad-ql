from pyparsing import *

class QL:
    # Booleans
    AND = '&&'
    OR = '||'
    NOT = '!'

    # Comparisons
    GT = '>'
    LT = '<'
    GTE = '>='
    LTE = '<='
    EQ = '='
    NEQ = '!='

    # Basic Arithmics
    ADD = '+'
    SUB = '-'
    MUL = '*'
    DIV = '/'

    # Defines
    IF = 'if'
    colon = ':'
    lcurly = '{'
    rcurly = '}'
    form_type = oneOf('form')
    field_type = oneOf('boolean string integer data decimal money currency')
    word = Word(alphas)
    identifier = word

    # Quoted data
    string = QuotedString('"')
    evaluation = QuotedString(quoteChar="(", endQuoteChar=")", escChar='\\',
                             unquoteResults=False)
    evaluation_unquoted = QuotedString(quoteChar="(", endQuoteChar=")",
                                       escChar='\\')
    codeblock = QuotedString(quoteChar="{", endQuoteChar="}", escChar='\\',
                             unquoteResults=False)
    codeblock_unquoted = QuotedString(quoteChar="{", endQuoteChar="}",
                                      escChar='\\')

    # form content
    question = string + identifier + Suppress(colon) + field_type
    statement = string + identifier + Suppress(colon) + field_type + EQ + \
                evaluation
    conditional = IF + Group(evaluation + Suppress(lcurly) + \
                Group(OneOrMore(Group(Or([statement,question])))) + Suppress(rcurly))

    # form items
    form_content = Or([conditional,statement,question])
    form_item = OneOrMore(Group(form_content))

    # outer form
    form = form_type + identifier + Suppress(lcurly) + form_item + \
                Suppress(rcurly)
