package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a FloatingTask in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task implements ReadOnlyTask {
  //@@author A0138411N
    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    public FloatingTask(TaskName taskName, Priority priority) throws IllegalValueException {
        super(taskName, 
              new TaskDate(Messages.MESSAGE_NO_START_DATE_SPECIFIED),
              new TaskTime(Messages.MESSAGE_NO_START_TIME_SET),
              new TaskDate(Messages.MESSAGE_NO_END_DATE_SPECIFIED),
              new TaskTime(Messages.MESSAGE_NO_END_TIME_SET), 
              priority);
    }

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public FloatingTask(ReadOnlyTask source) throws IllegalValueException {
        super(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, startDate, startTime, endDate, endTime, priority);
    }

}
