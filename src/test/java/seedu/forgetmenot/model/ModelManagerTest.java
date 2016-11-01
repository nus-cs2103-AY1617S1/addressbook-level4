package seedu.forgetmenot.model;

import org.junit.Test;

import seedu.forgetmenot.commons.exceptions.IllegalValueException;
import seedu.forgetmenot.testutil.TaskBuilder;
import seedu.forgetmenot.testutil.TestTask;

public class ModelManagerTest {

    @Test
    public void addRecurringTask_addDefaultNumberOfRecurringEventTask_addsTenInstancesToTaskManager() throws IllegalValueException {
        TestTask recurringTask =  new TaskBuilder().withName("recurring task").withStartTime("tomorrow 9pm")
                .withEndTime("tomorrow 10pm").withDone(false).withRecurrence("day")
                .build();
        
        
    }
}
