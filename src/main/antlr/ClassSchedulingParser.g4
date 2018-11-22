parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

createParam
	: (RET TAB WORD COLON (WORD (WORD)*)* (RET TAB TAB WORD COLON (COMMA WORD)*)*)+;

// Course
createCourse
    : COURSE COLON createParam;

// Class
createClass
    : CLASS COLON createParam;

// Classroom
createClassroom
    : CLASSROOM COLON createParam;

// Lecturer
createLecturer
    : LECTURER COLON createParam;

file
	: ((createCourse | createClass | createClassroom | createLecturer) RET)*;
