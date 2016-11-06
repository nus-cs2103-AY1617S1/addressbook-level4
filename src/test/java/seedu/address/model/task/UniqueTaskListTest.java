package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.stub.NameStub;
import seedu.address.model.task.stub.TaskStub;
import seedu.address.model.task.stub.UniqueTagListStub;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

//@@author A0135782Y
/**
 * Tests methods that have not been fully covered in the other tests
 *
 */
public class UniqueTaskListTest {
    UniqueTaskList taskList;
    UniqueTaskListHelper helper;
    @Before
    public void setUp() {
        taskList = new UniqueTaskList();
        helper = new UniqueTaskListHelper();
    }
    
    @Test
    public void addToTaskList_unitTest() throws Exception {
        taskList.add(new TaskStub());
        assert_taskList_contentMatch(1, 1);
    }
    
    @Test
    public void addToTaskList_integrationTest() throws Exception {
        Task toAdd = new Task(new NameStub(), new UniqueTagListStub());
        taskList.add(toAdd);
        assert_taskList_contentMatch(1, 1);
    }
    
    @Test
    public void add_duplicate_recurring_tasks_successful() throws Exception {
        TestTask toAdd = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        TestTask toAddRecurring = helper.buildNonFloatingTask(RecurringType.DAILY, "12oct 2016 11pm", "13oct 2016 12pm");
        taskList.add(toAdd);
        taskList.add(toAddRecurring);
        assert_taskList_contentMatch(1, 2);
    }
    
    @Test(expected = DuplicateTaskException.class)
    public void add_duplicate_non_recurring_tasks_throwException() throws Exception {
        TestTask toAdd = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        TestTask toAddRecurring = helper.buildNonFloatingTask(RecurringType.NONE, "12oct 2016 11pm", "13oct 2016 12pm");
        taskList.add(toAdd);
        taskList.add(toAddRecurring);
    }
    
    @Test(expected = TaskNotFoundException.class)
    public void remove_task_that_does_not_exist() throws Exception {
        TestTask toRemove = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        taskList.remove(toRemove);
    }
    
    private void assert_taskList_contentMatch(final int numOfTasks, final int numOfOccurrence) {
        assertEquals(taskList.getInternalTaskList().size(), numOfTasks);
        assertEquals(taskList.getInternalOccurrenceList().size(), numOfOccurrence);
    }
    
    class UniqueTaskListHelper {
        TestTask buildNonFloatingTask(RecurringType type, String startDate, String endDate) throws Exception {
            TaskBuilder builder = new TaskBuilder();
            return builder.withName("Non Floating Task").withStartDate(startDate)
                    .withEndDate(endDate).withRecurringType(type).build();
        }
    }
}
