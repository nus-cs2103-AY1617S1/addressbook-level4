package seedu.emeraldo.model.task;

import seedu.emeraldo.commons.util.CollectionUtil;
import seedu.emeraldo.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private DateTime dateTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, DateTime dateTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, dateTime, tags);
        this.description = description;
        this.dateTime = dateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getDateTime(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    //@@author A0139342H
    public void setDescription(Description description){
       assert !CollectionUtil.isAnyNull(description);
       this.description = description;
    }
    //@@author A0139196U
    public void setDateTime(DateTime dateTime){
        assert !CollectionUtil.isAnyNull(dateTime);
        this.dateTime = dateTime;
    }
    //@@author
    
    @Override
    public DateTime getDateTime() {
        return dateTime;
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
        return Objects.hash(description, dateTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
