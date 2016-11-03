package seedu.task.commons.util;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.task.commons.util.StringUtil;

public class StringUtilTest {

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
    public void getDistanceOfTwoStrings_twoSimiliarStrings_shouldReturnDifference(){
        String a = new String("Xu Chen");
        String b = new String("XuChen");
        
        String c = new String("Kitten");
        String d = new String("Kitsen");
        
        String e = new String("myString");
        String f = new String("mynString");
        
        String g = new String("an");
        String h = new String("di");
        
        assertEquals("Changing XuChen to Xu Chen requires operations:", 1, StringUtil.getDistance(a, b));
        assertEquals("Changing Kitten to Kitsen requires operations:", 1, StringUtil.getDistance(c, d));
        assertEquals("Changing myString to mynString requires operations:", 1, StringUtil.getDistance(e, f));
        assertEquals("Changing abcde to efgh requires operations:", 2, StringUtil.getDistance(g, h));
    }
    
    
    @Test
    public void isSimilar_twoNonEmptySimilarStrings_shouldReturnTrue(){
        String a = new String("XusChen");
        String b = new String("XuChen");
        
        String c = new String("long string");
        String d = new String("long");
        
        assertTrue("'Xu ssChen' and 'XuChen' are similiar", StringUtil.findMatch(a, b));
        assertTrue("Similiar because contains", StringUtil.findMatch(c, d));
    }
    
    @Test
    public void isSimiliar_oneEmptyString_shouldReturnFalse(){
        String a = new String("Xu ssChen");
        String b = null;

        assertFalse("'Xu ssChen' and '' are not similiar", StringUtil.findMatch(a, b));
    }


}
