parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

key
	: WORD;

value
	: (WORD)+ | (map)+;

map
	: key COLON value;

line
	: (TAB)* map;

createParam
	: line+;

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
	: (createCourse | createClass | createClassroom | createLecturer)*;
