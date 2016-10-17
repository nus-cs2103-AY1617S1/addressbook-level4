//@@author A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import seedu.oneline.commons.exceptions.IllegalValueException;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskTimeTest {
    
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
    
    
}
