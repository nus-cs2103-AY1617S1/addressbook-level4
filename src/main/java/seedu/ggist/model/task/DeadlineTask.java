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
    public DeadlineTask(TaskName taskName, TaskDate taskDate, TaskTime startTime, TaskTime endTime, UniqueTagList tags) {
        super(taskName, taskDate, startTime, endTime, tags);
    }

    /**
     * Copy constructor.
     */
    public DeadlineTask(ReadOnlyTask source) {
        this(source.getTaskName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getTags());
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskDate, startTime, endTime, tags);
    }
}
