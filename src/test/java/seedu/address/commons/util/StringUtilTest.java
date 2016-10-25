package seedu.address.commons.util;

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
    public void getDetails_exceptionGiven(){
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }
    
    //@@author A0138978E
    @Test
    public void addSpacesBetweenNumbersAndWords_combinedNumberWordStringsGiven() {
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("31Oct2016"), "31 Oct 2016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct312016"), "Oct 312016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct201631"), "Oct 201631");
    }
    
    @Test
    public void addSpacesBetweenNumbersAndWords_withStNdRdTh() {
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("1st"), "1st");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("2nd"), "2nd");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("3rd"), "3rd");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("4th"), "4th");
    	
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("1st31Oct2016"), "1st 31 Oct 2016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("2ndOct312016"), "2ndOct 312016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("3rdOct201631"), "3rdOct 201631");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("4thOct201631"), "4thOct 201631");
    	
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("31Oct20161st"), "31 Oct 20161st");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct3120162nd"), "Oct 3120162nd");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct2016313rd"), "Oct 2016313rd");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct2016314th"), "Oct 2016314th");
    }
    
    
    @Test
    public void addSpacesBetweenNumbersAndWords_separatedNumberWordStringsGiven() {
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("31 Oct 2016"), "31 Oct 2016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct 31 2016"), "Oct 31 2016");
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords("Oct 2016 31"), "Oct 2016 31");
    }
    
    @Test
    public void addSpacesBetweenNumbersAndWords_nullGiven() {
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords(null), null);
    }
    
    @Test
    public void addSpacesBetweenNumbersAndWords_emptyGiven() {
    	assertEquals(StringUtil.addSpacesBetweenNumbersAndWords(""), "");
    }


}
