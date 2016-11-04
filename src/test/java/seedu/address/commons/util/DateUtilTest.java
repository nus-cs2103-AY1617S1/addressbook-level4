package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the DateUtil methods.
 */
public class DateUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void isEmptyDate_emptyDate_true() {
        assertTrue(DateUtil.isEmptyDate(""));
    }
    
    @Test
    public void isEmptyDate_notEmptyDate_false() {
        assertFalse(DateUtil.isEmptyDate("0"));
    }
    
    @Test
    public void isValidDateFormat_validDateFormat_true() {
        assertTrue(DateUtil.isValidDateFormat("04.11.2016"));
        assertTrue(DateUtil.isValidDateFormat("04.11.2016-12"));
    }
    
    @Test
    public void isValidDateFormat_invalidDateFormat_false() {
        assertFalse(DateUtil.isValidDateFormat("4.11.2016"));
        assertFalse(DateUtil.isValidDateFormat("4 Nov"));
        assertFalse(DateUtil.isValidDateFormat("hi"));
    }

    @Test
    public void parseDate_validDate_success() {
        assertEquals(DateUtil.parseDate("04.11.2016"), "04.11.2016");
        assertEquals(DateUtil.parseDate("4.11.2016"), "04.11.2016");
        assertEquals(DateUtil.parseDate("04.11.2016-10"), "04.11.2016-10");
        assertEquals(DateUtil.parseDate("4.11.2016-10"), "04.11.2016-10");
        assertEquals(DateUtil.parseDate("4 Nov"), "04.11.2016");
        assertEquals(DateUtil.parseDate("4 Nov 10am"), "04.11.2016-10");
    }

    @Test
    public void parseDate_invalidDate_fail(){
        thrown.expect(IndexOutOfBoundsException.class);
        DateUtil.parseDate("hi");
    }
}
