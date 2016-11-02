package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;

//@@author A0138411N
/**
 * Represents a DeadlineTask in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class DeadlineTask extends Task implements ReadOnlyTask {
    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    public DeadlineTask(TaskName taskName, TaskDate endDate, TaskTime endTime, Priority priority) throws IllegalValueException {
        super(taskName, 
              new TaskDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED),
              new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
              endDate, endTime, priority);
    }
    
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public DeadlineTask(ReadOnlyTask source) throws IllegalValueException {
        super(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, startDate, startTime, endDate, endTime, priority);
    }
}
