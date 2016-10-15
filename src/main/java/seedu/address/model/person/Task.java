package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName name;
    private DateTime dueDate;
    private DueTime dueTime;
    private Address address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, DateTime dueDate, DueTime dueTime, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dueDate, dueTime, address, tags);
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate(), source.getDueTime(), source.getAddress(), source.getTags());
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public DateTime getDueDate() {
        return dueDate;
    }

    @Override
    public DueTime getDueTime() {
        return dueTime;
    }

    @Override
    public Address getAddress() {
        return address;
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
        return Objects.hash(name, dueDate, dueTime, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
