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

/* Entity */
COURSE     : C O U R S E;
FACILITY   : F A C I L I T Y;
CLASSROOM  : C L A S S R O O M;
CLASS      : C L A S S;
TIMESLOT   : T I M E S L O T;
LECTURER   : L E C T U R E R;

/* OPERATOR */
EQ                : '=';
OPEN_PARENTHESIS  : '(';
CLOSE_PARENTHESIS : ')';
OPEN_BRACKET      : '[';
CLOSE_BRACKET     : ']';
COMMA             : ',';
COLONS            : ':';

/* Preposition */
AND           : A N D;
WITH          : W I T H;
SET           : S E T;
END_STATEMENT : ';';

fragment DIGIT       : [0-9];
fragment ALPHA_LOWER : [a-z];
fragment ALPHA_UPPER : [A-Z];
fragment UNDERSCORE  : '_';
fragment CHAR        : UNDERSCORE | ALPHA_LOWER | ALPHA_UPPER;
fragment LETTER      : CHAR | DIGIT;
fragment INT         : DIGIT+;

/* Whitespace */
RET   : [\n]+;
TAB   : [\t]+;
WS    : [ \r]+ -> channel(HIDDEN);
WORD  : LETTER+;
