parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

// Facility
createFacility
    : CREATE FACILITY WORD END_STATEMENT;

showFacility
    : SHOW FACILITY WORD END_STATEMENT;

deleteFacility
    : DELETE FACILITY WORD END_STATEMENT;

// Classroom
createClassroom
    : CREATE CLASSROOM WORD WITH WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT; // masih perlu diganti regex

showClassroom
    : SHOW CLASSROOM WORD END_STATEMENT;

showAllClassroom
    : SHOW CLASSROOMS END_STATEMENT;

updateClassroom
    : UPDATE CLASSROOM WORD SET WITH WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT; // masih perlu diganti regex

deleteCLassroom
    : DELETE CLASSROOM WORD END_STATEMENT;

deleteAllClassroom
    : DELETE CLASSROOMS END_STATEMENT;

// Class
createClass
    : CREATE CLASS WORD WITH WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT;

showClass
    : SHOW CLASS WORD WITH WORD EQ WORD END_STATEMENT;

showAllClass
    : SHOW CLASSES END_STATEMENT;

updateClass
    : UPDATE CLASS WORD WITH WORD EQ WORD SET WITH WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT;

deleteClass
    : DELETE CLASS WORD END_STATEMENT;

deleteAllClass
    : DELETE CLASSES END_STATEMENT;