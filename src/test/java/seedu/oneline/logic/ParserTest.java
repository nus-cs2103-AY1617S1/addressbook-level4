package seedu.oneline.logic;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.logic.commands.CommandConstants;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.TaskField;

public class ParserTest {

    @Test
    public void parseStandardArguments() {
        String testName = "Test name";
        String testStartTime = "Time 1";
        String testEndTime = "Time 2";
        String testDeadline = "OMG DEADLINE";
        String testRecurrence = "Tuesday";
        String testTags = "#Tag1 #Tag2";
        String args;
        
        // All arguments specified
        args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_RECURRENCE + " " + testRecurrence + " " +
                testTags;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEquals(6, fields.entrySet().size());
        assertEquals(testName, fields.get(TaskField.NAME));
        assertEquals(testStartTime, fields.get(TaskField.START_TIME));
        assertEquals(testEndTime, fields.get(TaskField.END_TIME));
        assertEquals(testDeadline, fields.get(TaskField.DEADLINE));
        assertEquals(testRecurrence, fields.get(TaskField.RECURRENCE));
        assertEquals(testTags, fields.get(TaskField.TAG));
        
        // Optional arguments 1
        args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                testTags;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEquals(4, fields.entrySet().size());
        assertEquals(testName, fields.get(TaskField.NAME));
        assertEquals(testEndTime, fields.get(TaskField.END_TIME));
        assertEquals(testDeadline, fields.get(TaskField.DEADLINE));
        assertEquals(testTags, fields.get(TaskField.TAG));
        
        // Optional arguments 2
        args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
               CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_RECURRENCE + " " + testRecurrence;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEquals(2, fields.entrySet().size());
        assertEquals(testStartTime, fields.get(TaskField.START_TIME));
        assertEquals(testRecurrence, fields.get(TaskField.RECURRENCE));
    }
    
}
