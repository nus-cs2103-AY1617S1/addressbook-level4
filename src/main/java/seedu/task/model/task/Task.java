package seedu.task.model.task;

import java.util.Objects;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

/**
 * Represents a Task in the task list.
 * Guarantees: field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DateTime openTime;
    private DateTime closeTime;

    private UniqueTagList tags;
    public static final String MESSAGE_TASK_CONSTRAINTS = "Please ensure that your start and end time combination is valid.";


    /**
     * Assigns instance variables
     * TODO: Checks if dateTime pair is valid
     */
    public Task(Name name, DateTime openTime, DateTime closeTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getOpenTime(), source.getCloseTime(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
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
        //return Objects.hash(name, openTime, closeTime, isImportant, tags);
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public DateTime getOpenTime() {
        // TODO Auto-generated method stub
        return this.openTime;
    }

    @Override
    public DateTime getCloseTime() {
        // TODO Auto-generated method stub
         return this.closeTime;
    }

}
