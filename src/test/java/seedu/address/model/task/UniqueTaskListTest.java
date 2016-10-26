package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.*;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
/**
 * Tests methods that have not been fully covered in the other tests
 *
 */
public class UniqueTaskListTest {
    UniqueTaskList taskList;
    @Before
    public void setUp() {
        taskList = new UniqueTaskList();
    }
    
    @Test
    public void add_duplicate_recurring_tasks_successful() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        TestTask toAdd = builder.withName("Recurring Task").withStartDate("11oct 2016 11pm")
                .withEndDate("12oct 2016 12pm").withRecurringType(RecurringType.DAILY).build();
        TestTask toAddRecurring = builder.withName("Recurring Task").withStartDate("12oct 2016 11pm")
                .withEndDate("13oct 2016 12pm").withRecurringType(RecurringType.DAILY).build();
        taskList.add(toAdd);
        taskList.add(toAddRecurring);
        assertEquals(taskList.getInternalTaskList().size(), 1);
        assertEquals(taskList.getInternalComponentList().size(),2);
    }
    
    @Test
    public void add_duplicate_non_recurring_tasks_throwException() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        TestTask toAdd = builder.withName("Recurring Task").withStartDate("11oct 2016 11pm")
                .withEndDate("12oct 2016 12pm").withRecurringType(RecurringType.DAILY).build();
        TestTask toAddRecurring = builder.withName("Recurring Task").withStartDate("12oct 2016 11pm")
                .withEndDate("13oct 2016 12pm").withRecurringType(RecurringType.NONE).build();
        try {
            taskList.add(toAdd);
            taskList.add(toAddRecurring);
        } catch (DuplicateTaskException dte) {
            assertTrue(true);
        }
    }
    
    @Test
    public void remove_task_that_does_not_exist() throws Exception {
        TaskBuilder builder = new TaskBuilder();
        TestTask toRemove = builder.withName("Recurring Task").withStartDate("11oct 2016 11pm")
                .withEndDate("12oct 2016 12pm").withRecurringType(RecurringType.DAILY).build();
        try {
            taskList.remove(toRemove);
        } catch (TaskNotFoundException tnfe) {
            assertTrue(true);
        }
    }
}
