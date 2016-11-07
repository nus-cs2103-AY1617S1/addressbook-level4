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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtilTest {
	//@@author A0093896H
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void isValidDateString() {
        String[] validFormats = {
            "8 Oct 2015", "8/12/2014", "8-12-2000", 
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
    public void containsDateField() {
        assertTrue(DateTimeUtil.containsDateField("12/12/1234"));
        assertFalse(DateTimeUtil.containsDateField("12"));
        assertTrue(DateTimeUtil.containsDateField("12/12/1234 12:30"));
    }
    
    @Test
    public void containsTimeField() {
        assertFalse(DateTimeUtil.containsTimeField("12/12/1234"));
        assertTrue(DateTimeUtil.containsTimeField("12:30"));
        assertTrue(DateTimeUtil.containsTimeField("12"));
        assertTrue(DateTimeUtil.containsTimeField("12/12/1234 12:30"));
    }
    
    //@@author A0121643R
    @Test
    public void beforeOther() throws IllegalValueException {
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
    
    //@@author A0093896H
    @Test
    public void parseDateTimeString() {
        LocalDateTime ldt = LocalDateTime.of(1996, 1, 1, 20, 34);
        LocalDateTime ldtNoTime = LocalDateTime.of(1996, 1, 1, 0, 0);
        LocalDateTime ldtNoDate = LocalDateTime.now();
        ldtNoDate = ldtNoDate.of(ldtNoDate.getYear(), ldtNoDate.getMonth(), ldtNoDate.getDayOfMonth(), 20, 34);
        assertEquals(ldt, DateTimeUtil.parseDateTimeString("1/1/1996 20:34", TaskDate.TASK_DATE_ON));
        assertEquals(ldtNoTime, DateTimeUtil.parseDateTimeString("1/1/1996", TaskDate.TASK_DATE_ON));
        assertEquals(ldtNoDate, DateTimeUtil.parseDateTimeString("20:34", TaskDate.TASK_DATE_ON));
    }
    
    //@@author A0142421X
    @Test
    public void testPrettyPrintDate_equals() {
    	LocalDateTime ldt = LocalDateTime.now();
    	LocalDate date = ldt.toLocalDate();
    	String expectedPrettyDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    	assertEquals(DateTimeUtil.prettyPrintDate(date), expectedPrettyDate);
    }
    
    @Test
    public void testPrettyPrintTime_equals() {
    	LocalDateTime ldt = LocalDateTime.now();
    	LocalTime time = ldt.toLocalTime();
    	String expectedPrettyPrintTime = time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    	assertEquals(DateTimeUtil.prettyPrintTime(time), expectedPrettyPrintTime);
    }
    
    @Test
    public void testCombineLocalDateAndTime_time_not_null_equals() {
    	LocalDateTime ldt = LocalDateTime.now();
    	LocalDate date = ldt.toLocalDate();
    	LocalTime time = ldt.toLocalTime();
    	LocalDateTime expectedCombineLocalDateAndTime = LocalDateTime.of(date, time);
    	assertEquals(DateTimeUtil.combineLocalDateAndTime(date, time), expectedCombineLocalDateAndTime);
    }
    
    @Test
    public void testCombineLocalDateAndTime_time_null_equals() {
    	LocalDateTime ldt = LocalDateTime.now();
    	LocalDate date = ldt.toLocalDate();
    	LocalDateTime expectedCombineLocalDateAndTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59);
    	assertEquals(DateTimeUtil.combineLocalDateAndTime(date, null), expectedCombineLocalDateAndTime);
    }
    
  //@@author A0142421X-unused
    /*
     *@Test
    public void testBeforeOther_onDate_null_true() throws IllegalValueException {
    	TaskDate byDate = new TaskDate("8-12-2000", "by");
    	assertTrue(DateTimeUtil.beforeOther(null, byDate));
    }

    @Test
    public void testBeforeOther_byDate_null_true() throws IllegalValueException {
    	TaskDate onDate = new TaskDate("8-12-2000", "on");
    	assertTrue(DateTimeUtil.beforeOther(onDate, null));
    }
    
    @Test
    public void testBeforeOther_onDate_null_byDate_null_true() throws IllegalValueException {
    	assertTrue(DateTimeUtil.beforeOther(null, null));
    }
    */
    //@@author
}
