package seedu.tasklist.model.task;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;

import java.time.LocalDate;
import java.time.LocalTime;
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
     */
    public Task(Title title, DateTime startDateTime, Description description, DateTime endDateTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, startDateTime, description, endDateTime, tags);
        this.title = title;
        this.startDateTime = startDateTime;
        this.description = description;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isCompleted = false;
        
        initializeOverdue(endDateTime);
        initializeFloating(startDateTime, endDateTime);
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
        
        initializeOverdue(endDateTime);  //Check for errors
        initializeFloating(startDateTime, endDateTime); //Check for errors
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getStartDateTime(), source.getDescription(), source.getEndDateTime(), source.getTags(), source.isCompleted(), source.isOverdue(), source.isFloating());
    }
    

    /**
     * Check and assign task overdue status 
     * @param startDateTime cannot be null
     * @param endDateTime cannot be null
     */
    private void initializeOverdue(DateTime endDateTime) {
        if (!endDateTime.getDate().toString().isEmpty()) {
            if (endDateTime.getDate().getDate().isBefore(LocalDate.now())) {
                this.isOverdue = true;
            } else if (!endDateTime.getTime().toString().isEmpty() 
                    && endDateTime.getDate().getDate().isEqual(LocalDate.now()) 
                    && endDateTime.getTime().getTime().isBefore(LocalTime.now())) {
                this.isOverdue = true;
            }
        }
    }
    
    /**
     * Check and assign task floating status
     * @param startDateTime cannot be null
     * @param endDateTime cannot be null
     */
    private void initializeFloating(DateTime startDateTime, DateTime endDateTime) {
        if (startDateTime.getDate().toString().isEmpty() 
                && startDateTime.getTime().toString().isEmpty() 
                && endDateTime.getDate().toString().isEmpty() && endDateTime.getTime().toString().isEmpty()) {
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

}
