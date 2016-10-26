//@@author A0093896H
package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.model.task.TaskDate;

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
            assertNotNull(DateTimeUtil.parseDateTimeString(validFormat, TaskDate.TASK_DATE_ON));
        }
        for (String invalidFormat : invalidFormats) {
            assertNull(DateTimeUtil.parseDateTimeString(invalidFormat, TaskDate.TASK_DATE_ON));
        }
        
    }
}
