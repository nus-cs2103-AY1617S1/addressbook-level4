package seedu.todo.model;

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
public class PriorityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    
    @Test
    public void execute_setDefaultPriority_successful() throws Exception{
        TestDataHelper helper = new TestDataHelper();
        Task toBeSet = helper.generateFullTask(0);
                
        assertEquals(toBeSet.getPriority().priorityLevel, Priority.DEFAULT_PRIORITY);

        
    }

}
     
    
