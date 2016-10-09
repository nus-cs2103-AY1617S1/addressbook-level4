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
    public DeadlineTask(TaskName taskName, Date date, Time startTime, Time endTime, UniqueTagList tags) {
        this.taskName = taskName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
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
        return Objects.hash(taskName, date, startTime, endTime, tags);
    }
}
