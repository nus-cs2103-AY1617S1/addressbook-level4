//@@author A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import java.util.Calendar;

import seedu.oneline.commons.exceptions.IllegalValueException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskTimeTest {
    Calendar now;
    Calendar yesterday;
    Calendar tomorrow;
    int thisDay;
    int thisMonth;
    int thisYear;
    
    @Before
    public void setUp(){
        now = Calendar.getInstance();
        yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, -1);
        thisDay = now.get(Calendar.DAY_OF_MONTH);
        thisMonth = now.get(Calendar.MONTH);
        thisYear = now.get(Calendar.YEAR);
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private void IllegalValueExceptionThrown(String inputDateTime, String errorMessage) throws Exception{
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(errorMessage);    
        new TaskTime(inputDateTime);
    }

    // Tests for invalid datetime inputs to TaskTime constructor
    
    /**
     * Invalid equivalence partitions for datetime: null, other strings
     */
    
    @Test
    public void constructor_nullDateTime_assertionThrown() {
        thrown.expect(AssertionError.class);
        try {
            new TaskTime(null);
        } catch (IllegalValueException e){
            // should throw an assertion error instead of an illegal value exception
            assert false;
        }
    }

    @Test
    public void constructor_unsupportedDateTimeFormats_assertionThrown() throws Exception {
        IllegalValueExceptionThrown("day after", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("clearly not a time format", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("T u e s d a y", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
    }
    
    // Tests for valid datetime inputs
    
    /**
     * Valid equivalence partitions for datetime:
     *  - day month and year specified
     *  - only day and month specified
     *  - only day specified
     *  - relative date specified
     *  - empty string
     */

    @Test
    public void constructor_emptyDateTime() {
        try {
            TaskTime t = new TaskTime("");
            assert t.getDate() == null;
        } catch (Exception e) {
            assert false;
        }
    }
    
    @Test
    public void constructor_DMY() {
        String[] validFormats = new String[]{
                "5 October 2016", 
                "5 Oct 16", 
                "Oct 5 16",
                "10/5/16",
                "10/05/16"};
        try {
            for (String t : validFormats){
                TaskTime tTime = new TaskTime(t);
                Calendar tCal = DateUtils.toCalendar(tTime.getDate());
                assertTrue(tCal.get(Calendar.DAY_OF_MONTH) == 5);
                assertTrue(tCal.get(Calendar.MONTH) == Calendar.OCTOBER);
                assertTrue(tCal.get(Calendar.YEAR) == 2016);
            }
        } catch (Exception e) {
            assert false;
        }
    }
    
    /**
     * Equivalence partitions for fields with only date and month input
     *  - day and month has passed in current year
     *  - day and month has not passed in current year
     *  - day and month is the current day
     */
    
    @Test
    public void constructor_DM() {
        String[] validFormats = new String[]{
                "5 October",
                "5 Oct",
                "10/5"};
        try {
            
            for (String t : validFormats){
                Calendar fifthOct = Calendar.getInstance();
                fifthOct.set(thisYear, Calendar.OCTOBER, 5);
                
                
                TaskTime tTime = new TaskTime(t);
                Calendar tCal = DateUtils.toCalendar(tTime.getDate());
                assertTrue(tCal.get(Calendar.DAY_OF_MONTH) == 5);
                assertTrue(tCal.get(Calendar.MONTH) == Calendar.OCTOBER);
                
                // checks if date refers to previous year if day has passed
                assertTrue(now.compareTo(fifthOct) > 0 ? 
                        tCal.get(Calendar.YEAR) == thisYear + 1 : 
                            tCal.get(Calendar.YEAR) == thisYear);
            }
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Tests whether inputting a day and month that has passed will result
     * in next year's value in TaskTime object
     */
    @Test
    public void constructor_DMhasPassed() {
        // construct a string that represents MM/DD 
        // where MM/DD is the month and date of yesterday
        String yesterdayString = Integer.toString(yesterday.get(Calendar.DAY_OF_MONTH)) 
                + " " + Integer.toString(yesterday.get(Calendar.MONTH));
        try {
            TaskTime tTime = new TaskTime(yesterdayString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) + 1);
        } catch (Exception e) {
            assert false;
        }
    }
    
    /**
     * Tests whether inputting today's day and month causes 
     * the year stored in TaskTime to remain the same as today's year
     */
    @Test
    public void constructor_DMToday() {
        String todayString = Integer.toString(thisMonth) 
                + " " + Integer.toString(thisDay);
        try {
            TaskTime tTime = new TaskTime(todayString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == thisYear);
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * Tests whether inputting tomorrow's day and month causes 
     * the year stored in TaskTime to remain the same as tomorrow's year
     */
    @Test
    public void constructor_DMFuture() {
        // construct a string that represents MM/DD 
        // where MM/DD is the month and date of tomorrow
        String tomorrowString = Integer.toString(tomorrow.get(Calendar.DAY_OF_MONTH)) 
                + " " + Integer.toString(tomorrow.get(Calendar.MONTH));
        try {
            TaskTime tTime = new TaskTime(tomorrowString);
            Calendar tCal = DateUtils.toCalendar(tTime.getDate());
            assertTrue(tCal.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR));
        } catch (Exception e) {
            assert false;
        }
    }
    
}
