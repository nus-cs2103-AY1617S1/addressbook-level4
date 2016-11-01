//@@author A0093896H
package seedu.todo.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.TaskDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

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
    
    @Test
    public void containsDateField_test() {
        assertTrue(DateTimeUtil.containsDateField("12/12/1234"));
        assertFalse(DateTimeUtil.containsDateField("12"));
        assertTrue(DateTimeUtil.containsDateField("12/12/1234 12:30"));
    }
    
    @Test
    public void containsTimeField_test() {
        assertFalse(DateTimeUtil.containsTimeField("12/12/1234"));
        assertTrue(DateTimeUtil.containsTimeField("12:30"));
        assertTrue(DateTimeUtil.containsTimeField("12"));
        assertTrue(DateTimeUtil.containsTimeField("12/12/1234 12:30"));
    }
    
    @Test
    public void beforeOther_test() throws IllegalValueException {
        TaskDate onDate = new TaskDate("today", TaskDate.TASK_DATE_ON);
        TaskDate byDate = new TaskDate("six days later", TaskDate.TASK_DATE_BY);
        
        TaskDate onSameDate = new TaskDate("today 0900", TaskDate.TASK_DATE_ON);
        TaskDate bySameDate = new TaskDate("today 1900", TaskDate.TASK_DATE_ON);
        
        assertTrue(DateTimeUtil.beforeOther(onDate, byDate));
        assertFalse(DateTimeUtil.beforeOther(byDate, onDate));
        assertFalse(DateTimeUtil.beforeOther(onDate, onDate));
        
        assertTrue(DateTimeUtil.beforeOther(onSameDate, bySameDate));
        assertFalse(DateTimeUtil.beforeOther(bySameDate, onSameDate));
        assertFalse(DateTimeUtil.beforeOther(onSameDate, onSameDate));
    }
    
    @Test
    public void parseDateTimeString_test() {
        LocalDateTime ldt = LocalDateTime.of(1996, 1, 1, 20, 34);
        LocalDateTime ldtNoTime = LocalDateTime.of(1996, 1, 1, 0, 0);
        LocalDateTime ldtNoDate = LocalDateTime.now();
        ldtNoDate = ldtNoDate.of(ldtNoDate.getYear(), ldtNoDate.getMonth(), ldtNoDate.getDayOfMonth(), 20, 34);
        assertEquals(ldt, DateTimeUtil.parseDateTimeString("1/1/1996 20:34", TaskDate.TASK_DATE_ON));
        assertEquals(ldtNoTime, DateTimeUtil.parseDateTimeString("1/1/1996", TaskDate.TASK_DATE_ON));
        assertEquals(ldtNoDate, DateTimeUtil.parseDateTimeString("20:34", TaskDate.TASK_DATE_ON));
        
        
    }
}
