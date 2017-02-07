/**
 * Define a grammar called Hello
 */

grammar Hello;

root: 'form' ID block;

block: '{' question* '}';

question : ( ID':' STRING type computed_question* | statement );

type: ( 'boolean' | 'date' | 'decimal' | 'integer' | 'money' | 'string' ) ;

computed_question: '(' type '-' type | type '+' type ')' ;

statement
 : IF parenthesisExpr block (ELSE IF parenthesisExpr block)* (ELSE block)?
 | WHILE parenthesisExpr block
 ;

parenthesisExpr
 : '(' expr ')';

expr
 : atom relOp atom
 | atom boolOp atom
 | atom arithOp atom
 | '!' atom
 | atom
 ;

relOp
 : '==' | '!=' | '<=' | '>=' | '>' | '<';

boolOp
 : '&&' | '||';

arithOp
 : '+' | '-' | '/' | '*';

atom
 : DECIMAL
 | MONEY
 | INT
 | STRING
 | BOOL
 | DDMMYY
 | ID
 ;

// TODO look up conventions tokens/names capital letters
BOOL: ('true' | 'false');
IF : 'if';
ELSE : 'else';
WHILE : 'while';

ID:  ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;

INT: ('0'..'9')+;

TWO_DIGIT: ('0'..'9')('0'..'9');

DECIMAL : INT '.' INT | '.' INT;
MONEY : INT '.' TWO_DIGIT;

DDMMYY : TWO_DIGIT ('.' | '-' | '/') TWO_DIGIT ('.' | '-' | '/') TWO_DIGIT ; // TODO check valid date

STRING: ('"' .*? '"');

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

// http://stackoverflow.com/questions/14778570/antlr-4-channel-hidden-and-options
COMMENT 
    :   ( '//' ~[\r\n]* '\r'? '\n'
        | '/*' .*? '*/'
        ) -> skip
    ;
