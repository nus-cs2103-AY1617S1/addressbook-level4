package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DateTimeUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void isValidDateString_test() {
        String[] validFormats = {"8 Oct 2015", "8/12/2014", "8-12-2000", 
            "2/October/2103", "13 March 2013", "4 May 2013"};
        String[] invalidFormats = {"abcd", "adsa"};
        for (String validFormat : validFormats) {
            assertNotNull(DateTimeUtil.parseDateTimeString(validFormat));
        }
        for (String invalidFormat : invalidFormats) {
            assertNull(DateTimeUtil.parseDateTimeString(invalidFormat));
        }
        
    }


    @Test
    public void isValidTimeString_test() {
        String[] validFormats = {"13:30", "09:45", "12:34 pm", "12:45 am", "2:45 pm", "2:4 pm", "12:0"};
        String[] invalidFormats = {"33:30", "53:30pm"};
        for (String validFormat : validFormats) {
            assertNotNull(DateTimeUtil.parseDateTimeString(validFormat));
        }
        for (String invalidFormat : invalidFormats) {
            assertNull(DateTimeUtil.parseDateTimeString(invalidFormat));
        }

    }
}
