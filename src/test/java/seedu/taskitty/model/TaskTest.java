package seedu.taskitty.model;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskPeriod;
import seedu.taskitty.model.task.TaskTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author A0139930B
public class TaskTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void invalidDateFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("25-12-2016");
    }
    
    @Test
    public void invalidDate_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("29/2/2015");
    }
    
    @Test
    public void invalidTimeFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskTime date = new TaskTime("3pm");
    }
    
    @Test
    public void invalidTime_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("25:00");
    }
    
    @Test
    public void invalidPeriod_startDateAfterEndDate_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskPeriod period = new TaskPeriod(new TaskDate("25/12/2016"), new TaskTime("10:00"),
                        new TaskDate("24/12/2016"), new TaskTime("12:00"));
    }
    
    @Test
    public void invalidPeriod_startTimeAfterEndTime_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        TaskDate date = new TaskDate("25/12/2016");
        TaskPeriod period = new TaskPeriod(date, new TaskTime("10:00"),
                        date, new TaskTime("9:00"));
    }
}
