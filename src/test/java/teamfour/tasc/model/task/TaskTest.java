package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;
import teamfour.tasc.testutil.TaskBuilder;
import teamfour.tasc.testutil.TestTask;
import teamfour.tasc.testutil.TypicalTestTasks;

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
