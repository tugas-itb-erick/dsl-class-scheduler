parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

createParam
    : (RET TAB WORD COLONS (WORD (WORD)*)* (RET TAB TAB WORD COLONS (COMMA WORD)*)*)+;

// Course
createCourse
    : COURSE COLONS createParam END_STATEMENT;

// Class
createClass
    : CLASS COLONS createParam END_STATEMENT;

// Classroom
createClassroom
    : CLASSROOM createParam END_STATEMENT;

// Lecturer
createLecturer
    : LECTURER COLONS createParam END_STATEMENT;
