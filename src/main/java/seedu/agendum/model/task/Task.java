package seedu.agendum.model.task;

import seedu.agendum.commons.util.CollectionUtil;
import seedu.agendum.model.tag.UniqueTagList;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a Task in the to do list.
 * Only the task name is compulsory and it cannot be an empty string.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private UniqueTagList tags;
    
    // ================ Constructor methods ==============================

    /**
     * Constructor for a floating task (with no deadline/start time or end time)
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = null;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Constructor for a task with deadline only
     */
    public Task(Name name, LocalDateTime deadline, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = deadline;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Constructor for a task (event) with both a start and end time
     */
    public Task(Name name, LocalDateTime startDateTime, LocalDateTime endDateTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getTags());
        if (source.isCompleted()) {
            this.markAsCompleted();
        }
    }
    
    // ================ Getter methods ==============================

    @Override
    public Name getName() {
        return name;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public boolean isNotCompleted() {
        return (isCompleted == false);
    }
    
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    // ================ Setter methods ==============================
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void markAsCompleted() {
        this.isCompleted = true;
    }
    
    public void markAsUncompleted() {
        this.isCompleted = false;
    }
    
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = startDateTime;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    // ================ Other methods ==============================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
