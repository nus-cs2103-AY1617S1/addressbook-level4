package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a DeadlineTask in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class DeadlineTask extends Task implements ReadOnlyTask {

    /**
     * Every field must be present and not null.
     */
    public DeadlineTask(TaskName taskName, TaskDate taskDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority) {
        super(taskName, taskDate, startTime, endDate, endTime, priority);
    }

    /**
     * Copy constructor.
     */
    public DeadlineTask(ReadOnlyTask source) {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority());
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, startDate, startTime, endDate, endTime, priority);
    }
}
