package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isUnsignedPositiveInteger() {
        assertFalse(StringUtil.isUnsignedInteger(null));
        assertFalse(StringUtil.isUnsignedInteger(""));
        assertFalse(StringUtil.isUnsignedInteger("a"));
        assertFalse(StringUtil.isUnsignedInteger("aaa"));
        assertFalse(StringUtil.isUnsignedInteger("  "));
        assertFalse(StringUtil.isUnsignedInteger("-1"));
        assertFalse(StringUtil.isUnsignedInteger("0"));
        assertFalse(StringUtil.isUnsignedInteger("+1")); //should be unsigned
        assertFalse(StringUtil.isUnsignedInteger("-1")); //should be unsigned
        assertFalse(StringUtil.isUnsignedInteger(" 10")); //should not contain whitespaces
        assertFalse(StringUtil.isUnsignedInteger("10 ")); //should not contain whitespaces
        assertFalse(StringUtil.isUnsignedInteger("1 0")); //should not contain whitespaces

        assertTrue(StringUtil.isUnsignedInteger("1"));
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }

    @Test
    public void getDetails_exceptionGiven() {
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }

    @Test
    public void isEmpty() {
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isEmpty("    "));
        assertTrue(StringUtil.isEmpty("\t\n"));
        assertTrue(StringUtil.isEmpty("\r \r\n"));
        
        assertFalse(StringUtil.isEmpty("a"));
        assertFalse(StringUtil.isEmpty("  ah c "));
        assertFalse(StringUtil.isEmpty("12345"));
    }

    //@@author A0135805H
    @Test
    public void partitionStringAtPosition_nullString() {
        String[] expected = {"", "", ""};
        String[] outcome = StringUtil.partitionStringAtPosition(null, 0);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_emptyString() {
        String[] expected = {"", "", ""};
        String[] outcome = StringUtil.partitionStringAtPosition("", 0);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_positionTooLow() {
        String[] expected = {"", "", ""};
        String[] outcome = StringUtil.partitionStringAtPosition("I have a Pikachu", -1);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_positionTooHigh() {
        String[] expected = {"", "", ""};
        String[] outcome = StringUtil.partitionStringAtPosition("I have a Pikachu", 16);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_partitionLower() {
        String[] expected = {"", "I", " have a Pikachu"};
        String[] outcome = StringUtil.partitionStringAtPosition("I have a Pikachu", 0);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_partitionUpper() {
        String[] expected = {"I have a Pikach", "u", ""};
        String[] outcome = StringUtil.partitionStringAtPosition("I have a Pikachu", 15);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void partitionStringAtPosition_partitionMiddle() {
        String[] expected = {"I hav", "e", " a Pikachu"};
        String[] outcome = StringUtil.partitionStringAtPosition("I have a Pikachu", 5);
        assertArrayEquals(expected, outcome);
    }

}
