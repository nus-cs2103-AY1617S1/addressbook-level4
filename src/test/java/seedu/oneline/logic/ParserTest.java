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

//@@author A0140156R
public class ParserTest {

    String testName = "Test name";
    String testStartTime = "Sun Oct 16 21:35:45";
    String testEndTime = "Sun Oct 16 21:35:45";
    String testDeadline = "Sun Oct 16 21:35:45";
    String testTag = "Tag1";
    
    @Test
    public void parser_allArgs_success() {
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
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
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, testStartTime),
                new SimpleEntry<TaskField, String>(TaskField.END_TIME, testEndTime),
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, testDeadline),
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }
    
    @Test
    public void parser_oneArgName_success() {
        String args = testName;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.NAME, testName));
    }

    @Test
    public void parser_oneArgStartTime_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, testStartTime));
    }

    @Test
    public void parser_oneArgEndTime_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.END_TIME, testEndTime));
    }

    @Test
    public void parser_oneArgDeadline_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, testDeadline));
    }

    @Test
    public void parser_oneArgTag_success() {
        String args = CommandConstants.TAG_PREFIX + testTag;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }

    @Test
    public void parser_twoArgs_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
               CommandConstants.TAG_PREFIX + testTag;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, testStartTime),
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }
    
    @Test
    public void parser_threeArgs_success() {
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline;;
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
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, testDeadline));
    }    
    
    //@@author A0142605N
    // Collaborated with A0140156R for full coverage of parser
    
    @Test
    public void parser_allArgsReordered_success() {
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
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
                new SimpleEntry<TaskField, String>(TaskField.TAG, testTag));
    }
    
    @Test
    public void parser_tagInMiddle_fail() {
        // tags supposed to be specified at the end of the command
        boolean errorThrown = false;
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.TAG_PREFIX + testTag + 
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " ";
        try {
            Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            errorThrown = e.getMessage().equals("Categories should be the last fields in command");
        }
        assertTrue(errorThrown);
    }
    
    @Test
    public void parser_duplicateField_fail() {
        boolean errorThrown = false;
        String args = testName + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " + testStartTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " + testEndTime + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " + testDeadline + " " +
                CommandConstants.TAG_PREFIX + testTag;
        try {
            Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            errorThrown = e.getMessage().equals("There are more than 1 instances of .from in the command.");
        }
        assertTrue(errorThrown);
    }
    
    @Test
    public void parser_noArgs_throwError() {
        // if no arguments specified, throw an error
        boolean errorThrown = false;
        try {
            Parser.getTaskFieldsFromArgs("");
        } catch (IllegalCmdArgsException e) {
            errorThrown = e.getMessage().equals("No fields specified.");
        }
        assertTrue(errorThrown);
    }
    
    @Test
    public void parser_oneArgNoValue_success() {
        String args = CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE;
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, ""));
    }
    
    @Test
    public void parser_allArgsNoValue_success() {
        String args = 
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_START_TIME + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_END_TIME + " " +
                CommandConstants.KEYWORD_PREFIX + CommandConstants.KEYWORD_DEADLINE + " " +
                CommandConstants.TAG_PREFIX + "X";
        Map<TaskField, String> fields = null;
        try {
            fields = Parser.getTaskFieldsFromArgs(args);
        } catch (IllegalCmdArgsException e) {
            e.printStackTrace();
            assert false;
        }
        assertEqualFields(fields,
                new SimpleEntry<TaskField, String>(TaskField.START_TIME, ""),
                new SimpleEntry<TaskField, String>(TaskField.END_TIME, ""),
                new SimpleEntry<TaskField, String>(TaskField.DEADLINE, ""),
                new SimpleEntry<TaskField, String>(TaskField.TAG, "X"));
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
