package teamfour.tasc.model.task.util;

import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;

/**
 * Commonly used task utilities.
 */
public class TaskUtil {
    /**
     * Convert an uncompleted task to completed.
     * 
     * @param taskToConvert to completed
     * @return the same task but with completed = true
     * @throws TaskAlreadyCompletedException if task is already completed
     */
    public static Task convertToComplete(ReadOnlyTask taskToConvert) throws TaskAlreadyCompletedException {
        if (taskToConvert.getComplete().isCompleted() == true) {
            throw new TaskAlreadyCompletedException();
        }
        
        return new Task(taskToConvert.getName(),
                new Complete(true),
                taskToConvert.getDeadline(),
                taskToConvert.getPeriod(),
                taskToConvert.getRecurrence(),
                taskToConvert.getTags());
    }
}
