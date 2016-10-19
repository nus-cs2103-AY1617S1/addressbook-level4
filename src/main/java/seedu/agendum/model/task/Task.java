package seedu.agendum.model.task;

import seedu.agendum.commons.util.CollectionUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the to do list.
 * Only the task name is compulsory and it cannot be an empty string.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private Name name;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    
    // ================ Constructor methods ==============================

    /**
     * Constructor for a floating task (with no deadline/start time or end time)
     */
    public Task(Name name) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = null;
    }
    
    /**
     * Constructor for a task with deadline only
     */
    public Task(Name name, Optional<LocalDateTime> deadline) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = null;
        this.endDateTime = deadline.orElse(null);
    }

    /**
     * Constructor for a task with start and end datetime
     */
    public Task(Name name, Optional<LocalDateTime> startDateTime,
        Optional<LocalDateTime> endDateTime) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = startDateTime.orElse(null);
        this.endDateTime = endDateTime.orElse(null);
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(),
                source.getEndDateTime());
        if (source.isCompleted()) {
            this.markAsCompleted();
        }
    }
    
    // ================ Getter methods ==============================

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean isOverdue() {
        if (!getTaskTime().isPresent()) {
            return false;
        }
        return !isCompleted() && getTaskTime().get().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean isUpcoming() {
        if (!getTaskTime().isPresent()) {
            return false;
        }
        return !isCompleted() && !isOverdue() && getTaskTime().get().isBefore(
                LocalDateTime.now().plusDays(UPCOMING_DAYS_THRESHOLD));
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    private Optional<LocalDateTime> getTaskTime() {
        if (getStartDateTime().isPresent()) {
            return getStartDateTime();
        } else {
            return getEndDateTime();
        }
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
    
    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime = startDateTime.orElse(null);
    }
    
    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime = endDateTime.orElse(null);
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
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Task other) {
//        int compareCompletionStatus = compareCompletionStatus(other);
//        if (compareCompletionStatus != 0) {
//            return compareCompletionStatus;
//        }
        int compareTime = compareTime(other);
        if (compareTime != 0) {
            return compareTime;
        }
        int compareName = compareName(other);
        return compareName;
    }
    
    public int compareTime(Task other) {
        if (this.getTaskTime().isPresent() && other.getTaskTime().isPresent()) {
           return this.getTaskTime().get().compareTo(other.getTaskTime().get());
        } else if (this.getTaskTime().isPresent()) {
            return 1;
        } else if (other.getTaskTime().isPresent()) {
            return -1;
        }
        return 0;     
    }
    
    public int compareCompletionStatus(Task other) {
        return Boolean.compare(this.isCompleted, other.isCompleted);
    }

    public int compareName(Task other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    @Override
    public String toString() {
        return getAsText();
    }

}