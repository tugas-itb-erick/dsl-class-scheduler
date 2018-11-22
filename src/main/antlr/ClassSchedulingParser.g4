parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

createParam
	: (RET TAB WORD COLONS (WORD (WORD)*)* (RET TAB TAB WORD COLONS (COMMA WORD)*)*)+;

// Course
createCourse
    : COURSE COLONS createParam;

// Class
createClass
    : CLASS COLONS createParam;

// Classroom
createClassroom
    : CLASSROOM createParam;

// Lecturer
createLecturer
    : LECTURER COLONS createParam;
