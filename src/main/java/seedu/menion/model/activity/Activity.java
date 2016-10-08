package seedu.menion.model.activity;

import seedu.menion.commons.util.CollectionUtil;
import seedu.menion.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Activity implements ReadOnlyActivity {

    private Name name;
    private Deadline deadline;
    private Reminder reminder;
    private Priority priority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Deadline deadline, Reminder reminder, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadline, reminder, priority, tags);
        this.name = name;
        this.deadline = deadline;
        this.reminder = reminder;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Activity(ReadOnlyActivity source) {
        this(source.getName(), source.getDeadline(), source.getReminder(), source.getPriority(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Reminder getReminder() {
        return reminder;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
                || (other instanceof ReadOnlyActivity // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyActivity) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, reminder, priority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
