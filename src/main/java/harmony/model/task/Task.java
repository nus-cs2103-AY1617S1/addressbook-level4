package harmony.model.task;

import java.util.Objects;

import harmony.commons.util.CollectionUtil;
import harmony.model.tag.UniqueTagList;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Time time;
    private Date date;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Time time, Date date, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, time, date, tags);
        this.name = name;
        this.time = time;
        this.date = date;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getDate(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
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
        return Objects.hash(name, time, date, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
