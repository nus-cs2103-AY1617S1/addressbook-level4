package seedu.forgetmenot.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.model.task.Recurrence;
import seedu.forgetmenot.testutil.TaskBuilder;
import seedu.forgetmenot.testutil.TestTask;

public class ModelManagerTest {

    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringEventTask_addsTenInstancesToTaskManager()
            throws IllegalValueException {
        TestTask recurringTask = new TaskBuilder().withName("recurring task").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day").build();
        ModelManager testModel = new ModelManager();
        testModel.addRecurringTask(recurringTask);

        TestTask toCheck;
        StringBuilder addedTime = new StringBuilder("");
        for (int i = 0; i < Recurrence.DEFAULT_OCCURENCE; i++) {
            toCheck = new TaskBuilder().withName("recurring task").withStartTime(addedTime + "tomorrow 9pm")
                    .withEndTime(addedTime + "tomorrow 10pm").withDone(false).withRecurrence("day").build();
            System.out.println("toCheck: " + toCheck.toString());
            System.out.println("from model: " + testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i).toString());
            assertEquals(testModel.getTaskManager().getUniqueTaskList().getInternalList().get(i), toCheck);
            addedTime.insert(0, "day after ");

        }

    }
}
