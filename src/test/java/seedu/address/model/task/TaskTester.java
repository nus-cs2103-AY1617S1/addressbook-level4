package seedu.address.model.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.stub.NameStub;
import seedu.address.model.task.stub.TaskDateStub;
import seedu.address.model.task.stub.TaskOccurrenceStub;
import seedu.address.model.task.stub.UniqueTagListStub;

//@@author A0135782Y
/**
 * Unit Tester that tests the methods of the Task class
 *
 */
public class TaskTester {
    private Task task;
    
    @Test
    public void create_floatingTask() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createFloatingTask();
        assertEquals(task.getTaskType(), TaskType.FLOATING);
        
        task = helper.createNonFloatingTask(RecurringType.NONE);
        assertEquals(task.getTaskType(), TaskType.NON_FLOATING);
    }
    
    @Test(expected=AssertionError.class)
    public void setRecurringType_floatingTask_throwAssert() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createFloatingTask();
        task.setRecurringType(RecurringType.DAILY);
    }

    @Test
    public void setRecurringType_successful() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createNonFloatingTask(RecurringType.NONE);
        task.setRecurringType(RecurringType.DAILY);
        assertEquals(task.getRecurringType(), RecurringType.DAILY);
    }

    @Test
    public void setTaskType_successful() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createNonFloatingTask(RecurringType.NONE);
        task.setTaskType(TaskType.COMPLETED);
        assertEquals("Task type should be mutated", task.getTaskType(), TaskType.COMPLETED);
    }

    @Test(expected=AssertionError.class)
    public void appendTaskComponent_toNonRecurringTask_notAllowed() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createNonFloatingTask(RecurringType.NONE);
        TaskOccurrenceStub toAppend = helper.createTaskOccurenceStub(task);
        task.appendRecurringDate(toAppend);
    }

    @Test
    public void getLastAppendedComponent_success() throws Exception {
        TaskTesterHelper helper = new TaskTesterHelper();
        task = helper.createNonFloatingTask(RecurringType.DAILY);
        TaskOccurrenceStub toAppend = helper.createTaskOccurenceStub(task);
        task.appendRecurringDate(toAppend);
        TaskOccurrenceStub component = (TaskOccurrenceStub) task.getLastAppendedComponent();
        assertEquals("Task component just appended must be the last appended component", toAppend, component);
        assertEquals("Task occurrences should be properly appended", task.getTaskDateComponent().size(), 2);
    }
    
    class TaskTesterHelper {
        public Task createFloatingTask() throws IllegalValueException {
            return new Task(new NameStub("dummy"), new UniqueTagListStub());
        }
        
        public Task createNonFloatingTask(RecurringType type) throws IllegalValueException {
            return new Task(new NameStub("dummy"), new UniqueTagListStub(), new TaskDateStub(), new TaskDateStub(), type);
        }
        
        public TaskOccurrenceStub createTaskOccurenceStub(Task task) {
            return new TaskOccurrenceStub(task, new TaskDateStub(), new TaskDateStub());
        }
    }
}
