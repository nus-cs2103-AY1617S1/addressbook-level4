package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.CollectionUtil;
//@@author A0138411N
/**
 * Represents a EventTask in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task implements ReadOnlyTask {

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    public EventTask(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority) throws IllegalValueException {
        super(taskName, startDate, startTime, endDate, endTime, priority);
    }
    
    public EventTask(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority, 
                    boolean isDone, boolean isOverdue) throws IllegalValueException {
        super(taskName, startDate, startTime, endDate, endTime, priority);
        this.isDone = isDone;
        this.isOverdue = isOverdue;
    }
   
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public EventTask(ReadOnlyTask source) throws IllegalValueException {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority(), source.isDone(), source.isOverdue());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, startDate, startTime, endDate, endTime, priority);
    }
}
