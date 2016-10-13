package seedu.gtd.model.task;

import java.util.Objects;

import seedu.gtd.commons.util.CollectionUtil;
import seedu.gtd.model.person.ReadOnlyPerson;
import seedu.gtd.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DueDate dueDate;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate dueDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dueDate, tags);
        this.name = name;
        this.dueDate = dueDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate() , source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DueDate getDueDate() {
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
        return Objects.hash(name, dueDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}