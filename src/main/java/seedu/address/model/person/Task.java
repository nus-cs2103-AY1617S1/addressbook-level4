package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the dueDate book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Title task;
    private Group group;
    private Description description;
    private DueDate dueDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Title title, Group group, Description description, DueDate dueDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, group, description, dueDate, tags);
        this.task = title;
        this.group = group;
        this.description = description;
        this.dueDate = dueDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getGroup(), source.getDescription(), source.getAddress(), source.getTags());
    }

    @Override
    public Title getTitle() {
        return task;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DueDate getAddress() {
        return dueDate;
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
        return Objects.hash(task, group, description, dueDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
