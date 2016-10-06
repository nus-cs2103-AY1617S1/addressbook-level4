package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;
    private Date date;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, StartDateTime startDateTime, EndDateTime endDateTime, Date date, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startDateTime, endDateTime, date, tags);
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.date = date;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getDate(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public EndDateTime getEndDateTime() {
        return endDateTime;
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
        return Objects.hash(name, startDateTime, endDateTime, date, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
