package seedu.todo.model;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.TaskDate;
//@@author A0093896H
/**
 * Test class for taskdate
 */
public class TaskDateTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void taskDateCompareToTest() throws IllegalValueException {
        TaskDate today = new TaskDate("today", TaskDate.TASK_DATE_ON);
        TaskDate tmr = new TaskDate("tomorrow", TaskDate.TASK_DATE_ON);
        TaskDate yest = new TaskDate("yesterday", TaskDate.TASK_DATE_ON);
    
        TaskDate null1 = new TaskDate("", TaskDate.TASK_DATE_ON);
        TaskDate null2 = new TaskDate("", TaskDate.TASK_DATE_ON);
        
        assertEquals(0, null1.compareTo(null2));
        assertEquals(1, null1.compareTo(today));
        assertEquals(-1, today.compareTo(null1));
        assertEquals(0, today.compareTo(today));
        assertEquals(-1, today.compareTo(tmr));
        assertEquals(1, today.compareTo(yest));
    }
    
    
}
