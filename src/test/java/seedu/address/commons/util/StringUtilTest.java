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
    public void isUnsignedPositiveInteger_false() {
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
    }

    @Test
    public void isUnsignedPositiveInteger_true() {
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

    @Test
    public void substringIgnoreCase_true() {
        assertTrue(StringUtil.substringIgnoreCase("IgNore Case", "iGN"));
    }

    @Test
    public void substringIgnoreCase_false() {
        assertFalse(StringUtil.substringIgnoreCase("IgNore Case", "eCase"));
    }

    @Test
    public void containsIgnoreCase_true() {
        assertTrue(StringUtil.containsIgnoreCase("IgNore Case", "iGnOrE"));
    }

    @Test
    public void containsIgnoreCase_false() {
        assertFalse(StringUtil.containsIgnoreCase("IgNore Case", "aSe"));
    }
}
