parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

createFacility
    : CREATE FACILITY WORD END_STATEMENT;

createCourse
    : CREATE COURSE WORD WITH WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT;

updateCourse
    : UPDATE COURSE WORD SET WORD EQ WORD (AND WORD EQ WORD)* END_STATEMENT;

deleteCourse
    : DELETE COURSE WORD END_STATEMENT;

showCourse
    : SHOW COURSE WORD END_STATEMENT;

showCourses
    : SHOW COURSES END_STATEMENT;
