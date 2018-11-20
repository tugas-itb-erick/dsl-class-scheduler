parser grammar ClassSchedulingParser;

options {tokenVocab=ClassSchedulingLexer;}

createFacility
    : CREATE FACILITY WORD END_STATEMENT;
