package seedu.todo.model;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Recurrence;
import seedu.todo.model.task.Recurrence.Frequency;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDateTime;

import org.junit.Rule;
import static org.junit.Assert.assertEquals;

//@@author A0121643R
/**
 * Test class for priority
 */
public class PriorityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    
    @Test
    public void execute_setDefaultPriority_successful() throws IllegalValueException {
        TestDataHelper helper = new TestDataHelper();
        Task toBeSet = helper.generateFullTask(0);
                
        assertEquals(toBeSet.getPriority().priorityLevel, Priority.DEFAULT_PRIORITY);
    }
    
    @Test
    public void priorityCompareToTest() throws IllegalValueException {
        TestDataHelper helper = new TestDataHelper();
        Task t1 = helper.generateFullTask(0);
        Task t2 = helper.generateFullTask(1);
        
        assertEquals(0, t1.getPriority().compareTo(t2.getPriority()));
        
        t1.setPriority(new Priority(Priority.HIGH));
        assertEquals(-1, t1.getPriority().compareTo(t2.getPriority()));
        assertEquals(1, t2.getPriority().compareTo(t1.getPriority()));
        
        t2.setPriority(new Priority(Priority.MID));
        assertEquals(1, t2.getPriority().compareTo(t1.getPriority()));
        
        t1.setPriority(new Priority(Priority.LOW));
        assertEquals(-1, t2.getPriority().compareTo(t1.getPriority()));
    }

}
     
    
