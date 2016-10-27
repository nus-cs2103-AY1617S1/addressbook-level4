//@author A0144939R
package seedu.task.model.task;

import java.time.Instant;
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
    private boolean isCompleted;
    private boolean isImportant;

    private UniqueTagList tags;
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Please ensure that your start and end time combination is valid.";
    /**
     * Assigns instance variables
     * @throws IllegalValueException if DateTime pair is invalid
     */
    public Task(Name name, DateTime openTime, DateTime closeTime, boolean isImportant, boolean isCompleted, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = isCompleted;
        this.isImportant = isImportant;
        if (!isValidDateTimePair()) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
    }
    /**
     * Checks if openTime is before closeTime
     * @return
     */
    private boolean isValidDateTimePair() {
        if(openTime.getDateTimeValue().isPresent() && closeTime.getDateTimeValue().isPresent()) {
            Instant openTimeValue = openTime.getDateTimeValue().get();
            Instant closeTimeValue = closeTime.getDateTimeValue().get();
            return openTimeValue.isBefore(closeTimeValue);
        } else {
            return true;
        }
    }

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
        this(source.getName(), source.getOpenTime(), source.getCloseTime(), source.getImportance(), source.getComplete(), source.getTags());
    }
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DateTime getOpenTime() {
        return openTime;
    }

    @Override
    public DateTime getCloseTime() {
        return closeTime;
    }

    //@@author A0153467Y
    @Override
    public boolean getImportance() {
        return isImportant;
    }

    @Override
    public boolean getComplete() {
        return isCompleted;
    }
    //@@author

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
    //@@author A0153467Y
    /**
     * Retrieves an immutable version of the task. Will not mutate if task is changed afterwards.
     */
    public ReadOnlyTask getImmutable() {
        try {
            return new Task(this);
        } catch (IllegalValueException e) {
            assert false : "Impossible situation, as Task fields has been validated!";
            return null;
        }
    }
    
    /**
     * Sets the task's importance flag
     * @param isImportant is a variable to show whether the task is important or not
     */
    public void setIsImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    /**
     * Sets the task's completion flag
     * @param isCompleted is a variable which shows whether the task is completed or not
     */
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
	//@@author 
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        // return Objects.hash(name, openTime, closeTime, isImportant, tags);
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
