package seedu.task.model;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.TaskTime;

public class TaskTimeTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void invalidTimeFormat_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new TaskTime("1:30:00");
        fail();
    }
    
    @Test
    public void invalidTime_exceptionThrown() throws Exception {
        thrown.expect(IllegalValueException.class);
        new TaskTime("one:thirtypm");
        fail();
    }

}
