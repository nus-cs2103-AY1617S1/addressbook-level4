package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName name;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Priority priority;
    private Frequency frequency;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName taskName, Date date, Time startTime, Time endTime, Priority priority, Frequency frequency) {
        this.taskName = taskName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.frequency = frequency;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getPriority(), source.getFrequency(), source.getTags());
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
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public Frequency getFrequency() {
        return frequency;
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
        return Objects.hash(taksName, date, startTime, endTime, priority, frequency, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
