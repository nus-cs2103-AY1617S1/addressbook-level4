package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a FloatingTask in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask extends Task implements ReadOnlyTask {

    private TaskName taskName;
    private Date date;
    private Time startTime;
    private Time endTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public FloatingTask(TaskName taskName, UniqueTagList tags) {
        this.taskName = taskName;
        this.date = null;
        this.startTime = null;
        this.endTime = null;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyTask source) {
        this(source.getTaskName(), source.getTags());
    }

    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, date, startTime, endTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
