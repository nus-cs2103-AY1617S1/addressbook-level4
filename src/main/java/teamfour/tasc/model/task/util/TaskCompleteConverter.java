package teamfour.tasc.model.task.util;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.task.Complete;
import teamfour.tasc.model.task.Deadline;
import teamfour.tasc.model.task.Period;
import teamfour.tasc.model.task.ReadOnlyTask;
import teamfour.tasc.model.task.Recurrence;
import teamfour.tasc.model.task.Task;
import teamfour.tasc.model.task.exceptions.TaskAlreadyCompletedException;

/**
 * Converts a given uncompleted task to a
 * completed task. If the task is a recurrence,
 * then only the first occurrence is completed, and
 * the remaining recurring tasks will be uncompleted.
 */
public class TaskCompleteConverter {
    private final Task completedTask;
    private final Task uncompletedRemainingRecurringTask;
    
    /**
     * Constructor for converter.
     * @throws IllegalValueException 
     */
    public TaskCompleteConverter(ReadOnlyTask uncompletedTask)
            throws IllegalArgumentException, TaskAlreadyCompletedException, IllegalValueException {
        
        if (uncompletedTask == null) {
            throw new IllegalArgumentException();
        }
        
        if (uncompletedTask.getComplete().isCompleted() == true) {
            throw new TaskAlreadyCompletedException();
        }
        
        this.completedTask = new Task(uncompletedTask.getName(),
                new Complete(true),
                uncompletedTask.getDeadline(),
                uncompletedTask.getPeriod(),
                new Recurrence(),
                uncompletedTask.getTags());
        
        Recurrence oldRecurrence = uncompletedTask.getRecurrence();
        Recurrence newRecurrence = oldRecurrence.getRecurrenceWithOneFrequencyLess();
        
        if (newRecurrence == null) {
            this.uncompletedRemainingRecurringTask = null;
        } else {
            Deadline newDeadline = uncompletedTask.getDeadline();
            Period newPeriod = uncompletedTask.getPeriod();
            
            if (newDeadline.hasDeadline()) {
                newDeadline = new Deadline(
                        oldRecurrence.getNextDateAfterRecurrence(newDeadline.getDeadline()));
            }
            
            if (newPeriod.hasPeriod()) {
                newPeriod = new Period(
                        oldRecurrence.getNextDateAfterRecurrence(newPeriod.getStartTime()),
                        oldRecurrence.getNextDateAfterRecurrence(newPeriod.getEndTime()));
            }
            
            this.uncompletedRemainingRecurringTask = new Task(uncompletedTask.getName(),
                new Complete(false),
                newDeadline,
                newPeriod,
                newRecurrence,
                uncompletedTask.getTags());
        }
    }
    
    public Task getCompletedTask() {
        return completedTask;
    }
    
    public Task getUncompletedRemainingRecurringTask() {
        return uncompletedRemainingRecurringTask;
    }
}
