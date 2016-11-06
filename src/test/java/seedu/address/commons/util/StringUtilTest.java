package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
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
    public void getDetails_exceptionGiven(){
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }
    
    
    //@@author A0093960X
    @Test
    public void applyStringAtPosition_nullInput_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.applyStringAtPosition(null, "valid string", 0);
    }
    
    @Test
    public void applyStringAtPosition_nullExtraString_assertionError() {
        thrown.expect(AssertionError.class);
        StringUtil.applyStringAtPosition("valid string", null, 0);
    }
    
    @Test
    public void applyStringAtPosition_ValidStringsLessThanZeroPosition_extraInFrontOfInput() {
        // boundary value -1
        assertEquals(StringUtil.applyStringAtPosition("input", "extrastring", -1), "extrastringinput");
    }
    
    @Test
    public void applyStringAtPosition_ValidStringsGreaterThanInputLengthPosition_extraBehindInput() {
        // boundary value 6 (input length 5)
        assertEquals(StringUtil.applyStringAtPosition("input", "extrastring", 6), "inputextrastring");
    }

    @Test
    public void applyStringAtPosition_ValidStringsValidPositionZero_extraStringInPosition() {
        // boundary value 0
        assertEquals(StringUtil.applyStringAtPosition("input", "extrastring", 0), "extrastringinput");
    }
    
    @Test
    public void applyStringAtPosition_ValidStringsValidPosition_extraStringInPosition() {
        // between boundaries
        assertEquals(StringUtil.applyStringAtPosition("input", "extrastring", 3), "inpextrastringut");
    }
    
    @Test
    public void applyStringAtPosition_ValidStringsValidPositionInputStringLength_extraStringInPosition() {
        // boundary value 5 (input length 5)
        assertEquals(StringUtil.applyStringAtPosition("input", "extrastring", 5), "inputextrastring");
    }

}
