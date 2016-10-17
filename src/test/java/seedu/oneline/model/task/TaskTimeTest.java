//@@author A0138848M
package seedu.oneline.model.task;

import static org.junit.Assert.*;

import seedu.oneline.commons.exceptions.IllegalValueException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TaskTimeTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private void IllegalValueExceptionThrown(String inputTime, String errorMessage) throws Exception{
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(errorMessage);    
        new TaskTime(inputTime);
    }

    // Tests for invalid time inputs to TaskTime constructor
    
    /**
     * Invalid equivalence partitions for time: null, empty, other strings
     */
    
    @Test
    public void constructor_nullTime_assertionThrown() {
        thrown.expect(AssertionError.class);
        try {
            new TaskTime(null);
        } catch (IllegalValueException e){
            // should throw an assertion error instead of an illegal value exception
            assert false;
        }
    }

    @Test
    public void constructor_emptyTime_assertionThrown() throws Exception {
        IllegalValueExceptionThrown("", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
    }

    @Test
    public void constructor_unsupportedTimeFormats_assertionThrown() throws Exception {
        IllegalValueExceptionThrown("day after", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("clearly not a time format", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
        IllegalValueExceptionThrown("Tuesd", TaskTime.MESSAGE_TASK_TIME_CONSTRAINTS);
    }
    

}
