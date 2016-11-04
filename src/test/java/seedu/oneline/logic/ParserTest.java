//@@author A0140156R

package seedu.oneline.logic;

import static org.junit.Assert.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.logic.commands.CommandConstants;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.task.TaskField;

public class ParserTest {

    String testName = "Test name";
    String testStartTime = "Sun Oct 16 21:35:45";
    String testEndTime = "Sun Oct 16 21:35:45";
    String testDeadline = "Sun Oct 16 21:35:45";
    String testRecurrence = "Tuesday";
    String testTag = "Tag1";
    
    @Test
    public void parser_allArgs_success() {
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_RECURRENCE + " " + testRecurrence + " " +
                CommandConstants.TAG_PREFIX + testTag;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.NAME, testName),
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, testStartTime),
                new SimpleEntry<TaskField, String>(TaskField.END_TIME, testEndTime),
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, testDeadline),
                new SimpleEntry<TaskField, String>(TaskField.RECURRENCE, testRecurrence),
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }
    // KEN TODO EXPLAIN DIFF EQUIV CLASSES
    @Test
    public void parser_optionalArgs1_success() {
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.TAG_PREFIX + testTag;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.NAME, testName),
                new SimpleEntry<TaskField, String>(TaskField.END_TIME, testEndTime),
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, testDeadline),
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }
    
    @Test
    public void parser_optionalArgs2_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
               CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_RECURRENCE + " " + testRecurrence;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, testStartTime),
                new SimpleEntry<TaskField, String>(TaskField.RECURRENCE, testRecurrence));
    }
    
    @SafeVarargs
    private final void assertEqualFields(Map<TaskField, String> actualFields, Entry<TaskField, String> ... expectedFields) {
        assertEquals(actualFields.size(), expectedFields.length);
        for (int i = 0; i < expectedFields.length; i++) {
            Entry<TaskField, String> entry = expectedFields[i];
            String expectedFieldValue = entry.getValue();
            String actualFieldValue = actualFields.get(entry.getKey());
            assertEquals(expectedFieldValue, actualFieldValue);
        }
    }
    
}
