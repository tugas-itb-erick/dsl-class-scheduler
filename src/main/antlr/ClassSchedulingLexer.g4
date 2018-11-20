lexer grammar ClassSchedulingLexer;

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];

/* CRUD */
CREATE : C R E A T E;
SHOW   : S H O W;
UPDATE : U P D A T E;
DELETE : D E L E T E;

/* Entity */
FACILITY   : F A C I L I T Y;
CLASSROOM  : C L A S S R O O M;
CLASSROOMS : C L A S S R O O M S;
CLASS      : C L A S S;
CLASSES    : C L A S S E S;
COURSE     : C O U R S E;
COURSES    : C O U R S E S;
TIMESLOT   : T I M E S L O T;
TIMESLOTS  : T I M E S L O T S;

/* OPERATOR */
EQ : '=';
OPEN_PARENTHESIS: '(';
CLOSE_PARENTHESIS: ')';

/* Preposition */
AND: A N D;
SET: S E T;
WITH: W I T H;
END_STATEMENT: ';';

fragment DIGIT       : [0-9];
fragment ALPHA_LOWER : [a-z];
fragment ALPHA_UPPER : [A-Z];
fragment UNDERSCORE  : '_';
fragment CHAR        : UNDERSCORE | ALPHA_LOWER | ALPHA_UPPER;
fragment LETTER      : CHAR | DIGIT;
fragment INT         : DIGIT+;

/* Whitespace */
WS : [ \t\r\n]+ -> channel(HIDDEN);
WORD : LETTER+;

/* PARAMS */
//VALUE      : INT | WORD;
//PARAM      : WORD EQ VALUE;
//NEXT_PARAM : AND PARAM;
//PARAMS     : PARAM NEXT_PARAM*;
//PREDICATE  : WITH PARAMS;
