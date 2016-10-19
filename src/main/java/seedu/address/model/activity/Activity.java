package seedu.address.model.activity;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;

import java.util.Objects;

/**
 * Represents a Task in the Lifekeeper.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Activity implements ReadOnlyActivity {

    protected Name name;
    protected Reminder reminder;

    protected UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, Reminder reminder, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, reminder, tags);
        this.name = name;
        this.reminder = reminder;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Activity(ReadOnlyActivity source) {
        this(source.getName(), source.getReminder(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Reminder getReminder() {
        return reminder;
    }
    
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
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
        return Objects.hash(name, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
