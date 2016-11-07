package seedu.task.model;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.TaskDate;

public class TaskDateTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void invalidDateFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new TaskDate("1\3\2016");
        fail();
    }
    
    @Test
    public void invalidDate_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new TaskDate("in/valid/date");
        fail();
    }

}
