package seedu.forgetmenot.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.Recurrence;
import seedu.forgetmenot.testutil.TaskBuilder;
import seedu.forgetmenot.testutil.TestTask;

public class ModelManagerTest {

    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringEventTask_addsNineInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring task").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
            addedTime.insert(0, "day after ");
            toCheck = new TaskBuilder().withName("recurring task").withStartTime(addedTime + "tomorrow 9pm")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("day").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }

    @Test
    public void addRecurringTask_addSpecifiedNumberOfRecurringEventTask_addsSpecifiedInstancesToTaskManager()
            throws IllegalValueException {
        String specifiedOccurenceKeyword = " x";
        String specifiedOccurences = "5";
        TestTask recurringTask = new TaskBuilder().withName("recurring task 5 times").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false)
                .withRecurrence("2 days" + specifiedOccurenceKeyword + specifiedOccurences).build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Integer.parseInt(specifiedOccurences) - 1; i++) {
            addedTime.insert(0, "2 days after ");
            toCheck = new TaskBuilder().withName("recurring task 5 times").withStartTime(addedTime + "tomorrow 9pm")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("2 days").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }
    
    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringDeadlineTask_addsNineInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring deadline task").withStartTime("")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
            addedTime.insert(0, "day after ");
            toCheck = new TaskBuilder().withName("recurring deadline task").withStartTime("")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("day").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }
    
    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringStartOnlyTask_addsNineInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring start only task").withStartTime("tmr 10am")
                .withEndTime("").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE - 1; i++) {
            addedTime.insert(0, "day after ");
            toCheck = new TaskBuilder().withName("recurring start only task").withStartTime(addedTime + "tmr 10am")
                    .withEndTime("").withDone(false).withRecurrence("day").build();
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);

        }
    }
    
}
