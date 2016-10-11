package seedu.emeraldo.model.task;

import seedu.emeraldo.commons.util.CollectionUtil;
import seedu.emeraldo.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description Description;
    private Phone phone;
    private DateTime dateTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description Description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(Description, phone, dateTime, tags);
        this.Description = Description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return Description;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public DateTime getDateTime() {
        return dateTime;
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
        return Objects.hash(Description, phone, dateTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
