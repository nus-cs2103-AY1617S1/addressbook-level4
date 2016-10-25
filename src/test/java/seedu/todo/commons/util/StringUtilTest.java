package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

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
        // EP: null
        assertTrue(StringUtil.isEmpty(null));
        
        // EP: empty string (length zero) 
        assertTrue(StringUtil.isEmpty(""));
        
        // EP: whitespace characters only 
        assertTrue(StringUtil.isEmpty("    "));
        assertTrue(StringUtil.isEmpty("\t\n"));
        assertTrue(StringUtil.isEmpty("\r \r\n"));
        
        // EP: Non-empty strings 
        assertFalse(StringUtil.isEmpty("a")); // One character 
        assertFalse(StringUtil.isEmpty("  ah c ")); // Leading and trailing whitespace
        assertFalse(StringUtil.isEmpty(" c ah c")); 
        assertFalse(StringUtil.isEmpty("ah c ")); 
        
        assertFalse(StringUtil.isEmpty("12345")); // Non-alphabet characters
        assertFalse(StringUtil.isEmpty("!@#*&")); // Non-alphanumeric characters  
    }

    //@@author A0135805H
    @Test
    public void partitionStringAtPosition_emptyString() {
        String[] expected = {"", "", ""};

        //Tests null string
        testPartitionStringAtPositionHelper(null, 0, expected);

        //Test empty String
        testPartitionStringAtPositionHelper("", 0, expected);
    }

    @Test
    public void partitionStringAtPosition_positionOutOfBounds() {
        String input = "I have a Pikachu";
        String[] expected = {"", "", ""};

        //Tests position too low
        testPartitionStringAtPositionHelper(input, -1, expected);

        //Tests position too high
        testPartitionStringAtPositionHelper(input, 16, expected);
    }

    @Test
    public void partitionStringAtPosition_partitionCorrectly() {
        String input = "I have a Pikachu";

        //Test lower bound
        testPartitionStringAtPositionHelper(input, 0, new String[] {"", "I", " have a Pikachu"});

        //Test upper bound
        testPartitionStringAtPositionHelper(input, 15, new String[] {"I have a Pikach", "u", ""});

        //Test normal partition
        testPartitionStringAtPositionHelper(input, 5, new String[] {"I hav", "e", " a Pikachu"});
    }

    /**
     * Helper method to test partitionStringAtPosition(...).
     * @param input String to be partitioned.
     * @param position Position where partition should take place.
     * @param expected Expected output as String array.
     */
    private void testPartitionStringAtPositionHelper(String input, int position, String[] expected) {
        String[] outcome = StringUtil.partitionStringAtPosition(input, position);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void splitString_emptyInput() {
        String[] expected = new String[0];

        //Test null input.
        testSplitStringHelper(null, expected);

        //Test empty input.
        testSplitStringHelper("", expected);

        //Test only space and commas.
        testSplitStringHelper(" , ,   ,,, ,,,, , , ,,, , ,", expected);
    }

    @Test
    public void splitString_validInput() {
        //Input does not include space and comma
        testSplitStringHelper("!@(*&$!R#@%", new String[]{"!@(*&$!R#@%"});

        //Test one element
        testSplitStringHelper("Pichu-Pikachu_RAICHU's", new String[] {"Pichu-Pikachu_RAICHU's"});

        //Test multiple element split by space and comma
        testSplitStringHelper("an apple a, day, keeps , , doctor ,,, away", new String[] {"an", "apple", "a", "day", "keeps", "doctor", "away"});
    }

    /**
     * Helper method to test splitString(...).
     * @param input String to be split.
     * @param expected Expected output as String array.
     */
    private void testSplitStringHelper(String input, String[] expected) {
        String[] outcome = StringUtil.splitString(input);
        assertArrayEquals(expected, outcome);
    }

    @Test
    public void testConvertListToString_emptyList() {
        String expected = "";

        //Test null list
        testConvertListToStringHelper(null, expected);

        //Test empty list
        testConvertListToStringHelper(new String[0], expected);
    }

    @Test
    public void testConvertListToString_validInput() {
        //Test one element
        testConvertListToStringHelper(new String[]{"applepie123!"}, "applepie123!");

        //Test several elements
        testConvertListToStringHelper(new String[]{"this", "is", "apple", "pen"}, "this, is, apple, pen");
    }

    /**
     * Helper method to test splitString(...).
     * @param input String to be split.
     * @param expected Expected output as String array.
     */
    private void testConvertListToStringHelper(String[] input, String expected) {
        String outcome = StringUtil.convertListToString(input);
        assertEquals(expected, outcome);
    }

}
