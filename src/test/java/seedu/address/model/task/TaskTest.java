package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.exceptions.TaskAlreadyCompletedException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TypicalTestTasks;

public class TaskTest {

    @Test
    public void convertToComplete_uncompletedTask_becomesCompleted()
            throws IllegalValueException, TaskAlreadyCompletedException {
        TestTask uncompletedTask = new TypicalTestTasks().submitPrototype;
        uncompletedTask.setComplete(new Complete(false));

        ReadOnlyTask completedTask = Task.convertToComplete(uncompletedTask);
        assertTrue(completedTask.getComplete().isCompleted());
    }

    @Test (expected=TaskAlreadyCompletedException.class)
    public void convertToComplete_completedTask_throwsException() throws TaskAlreadyCompletedException {
        TestTask completedTask = new TypicalTestTasks().submitPrototype;
        completedTask.setComplete(new Complete(true));
        
        Task.convertToComplete(completedTask);
    }
}
