package seedu.tasklist.model.task;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;
import java.util.Objects;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {    
    private Title title;
    private Description description;
    private DateTime startDateTime;
    private DateTime endDateTime;

    private UniqueTagList tags;
    
    private boolean isCompleted;
    private boolean isOverdue;
    private boolean isFloating;

    /**
     * Every field must be present and not null.
     * @throws IllegalValueException 
     */
    public Task(Title title, DateTime startDateTime, Description description, DateTime endDateTime, UniqueTagList tags) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(title, startDateTime, description, endDateTime, tags);
        
        validateDateTime(startDateTime, endDateTime);
        
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
        
        initializeOverdue();
        initializeFloating();
    }
    
    /**
     * Every field must be present and not null.
     */
    public Task(Title title, DateTime startDateTime, Description description, DateTime endDateTime, UniqueTagList tags, boolean isCompleted, boolean isOverdue, boolean isFloating) {
        assert !CollectionUtil.isAnyNull(title, startDateTime, description, endDateTime, tags, isCompleted, isOverdue, isFloating);
        
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = isCompleted;
        this.isOverdue = isOverdue;
        this.isFloating = isFloating;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getStartDateTime(), source.getDescription(), source.getEndDateTime(), source.getTags(), source.isCompleted(), source.isOverdue(), source.isFloating());
    }
    
    /**
     * Check whether startDateTime is before endDateTime
     * @throws IllegalValueException if startDateTime is before endDateTime
     */
    private void validateDateTime(DateTime startDateTime, DateTime endDateTime) throws IllegalValueException {
        if (startDateTime.isDateTimeAfter(endDateTime)) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_TIME_ENTRY);
        }
    }
    
    /**
     * Initialize overdue task status 
     */
    private void initializeOverdue() {
        if (this.endDateTime.isDateTimeAfterCurrentDateTime()) {
            this.isOverdue = true;
        } else {
            this.isOverdue = false;
        }
    }
    
    /**
     * Initialize floating task status
     */
    private void initializeFloating() {
        if (this.startDateTime.isDateTimeEmpty() && this.endDateTime.isDateTimeEmpty()) {
            this.isFloating = true;
        } else {
            this.isFloating = false;
        }
    }
    
    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean isOverdue) {
        this.isOverdue = isOverdue;
    }
    
    public boolean isFloating() {
        return isFloating;
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
        return Objects.hash(title, startDateTime, description, endDateTime, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    //@@author A0153837X
    public String timeTask(){
    	return (this.getEndDateTime().timeLeft());
    }

}
