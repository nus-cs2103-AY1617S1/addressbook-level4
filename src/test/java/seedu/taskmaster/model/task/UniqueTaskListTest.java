package seedu.taskmaster.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.taskmaster.model.task.RecurringType;
import seedu.taskmaster.model.task.Task;
import seedu.taskmaster.model.task.UniqueTaskList;
import seedu.taskmaster.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.taskmaster.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.taskmaster.model.task.stub.NameStub;
import seedu.taskmaster.model.task.stub.TaskStub;
import seedu.taskmaster.model.task.stub.UniqueTagListStub;
import seedu.taskmaster.testutil.TaskBuilder;
import seedu.taskmaster.testutil.TestTask;

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
        assertTaskListContentMatch(1, 1);
    }
    
    @Test
    public void addToTaskList_integrationTest() throws Exception {
        Task toAdd = new Task(new NameStub(), new UniqueTagListStub());
        taskList.add(toAdd);
        assertTaskListContentMatch(1, 1);
    }
    
    @Test
    public void add_duplicate_recurring_tasks_successful() throws Exception {
        TestTask toAdd = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        TestTask toAddRecurring = helper.buildNonFloatingTask(RecurringType.DAILY, "12oct 2016 11pm",
                                                              "13oct 2016 12pm");
        taskList.add(toAdd);
        taskList.add(toAddRecurring);
        assertTaskListContentMatch(1, 2);
    }
    
    @Test(expected = DuplicateTaskException.class)
    public void add_duplicate_non_recurring_tasks_throwException() throws Exception {
        TestTask toAdd = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        TestTask toAddRecurring = helper.buildNonFloatingTask(RecurringType.NONE, "12oct 2016 11pm",
                                                              "13oct 2016 12pm");
        taskList.add(toAdd);
        taskList.add(toAddRecurring);
    }
    
    @Test(expected = TaskNotFoundException.class)
    public void remove_task_that_does_not_exist() throws Exception {
        TestTask toRemove = helper.buildNonFloatingTask(RecurringType.DAILY, "11oct 2016 11pm", "12oct 2016 12pm");
        taskList.remove(toRemove);
    }
    
    /**
     * Helps to assert if the task list matches with the expected number of task and occurrences.
     */
    private void assertTaskListContentMatch(final int numOfTasks, final int numOfOccurrence) {
        assertEquals(taskList.getInternalTaskList().size(), numOfTasks);
        assertEquals(taskList.getInternalOccurrenceList().size(), numOfOccurrence);
    }
    
    /**
     * A utility class to generate test data.
     */
    class UniqueTaskListHelper {
        /**
         * Builds a non floating task
         */
        TestTask buildNonFloatingTask(RecurringType type, String startDate, String endDate) throws Exception {
            TaskBuilder builder = new TaskBuilder();
            return builder.withName("Non Floating Task").withStartDate(startDate)
                    .withEndDate(endDate).withRecurringType(type).build();
        }
    }
}
