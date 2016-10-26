package seedu.agendum.model.task;

import seedu.agendum.commons.util.CollectionUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

//@@author A0133367E
/**
 * Represents a Task in the to do list.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private Name name;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime lastUpdatedTime;
    
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
        setLastUpdatedTimeToNow();
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
        setLastUpdatedTimeToNow();
    }
    
    /**
     * Constructor for a task (event) with both a start and end time
     */
    public Task(Name name, Optional<LocalDateTime> startDateTime,
            Optional<LocalDateTime> endDateTime) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.isCompleted = false;
        this.startDateTime = startDateTime.orElse(null);
        this.endDateTime = endDateTime.orElse(null);
        setLastUpdatedTimeToNow();
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime());
        if (source.isCompleted()) {
            this.markAsCompleted();
        }
        setLastUpdatedTime(source.getLastUpdatedTime());
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
    public boolean isUpcoming() {
        return !isCompleted() && hasTime() && getTaskTime().isBefore(
                LocalDateTime.now().plusDays(UPCOMING_DAYS_THRESHOLD));
    }

    @Override
    public boolean isOverdue() {
        return !isCompleted() && hasTime() && getTaskTime().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean hasTime() {
        return (getStartDateTime().isPresent() || getEndDateTime().isPresent());
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime);
    }

    @Override
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * Pre-condition: Task has a start or end time
     * Return the (earlier) time associated with the task (assumed to be start time)
     */
    private LocalDateTime getTaskTime() {
        assert hasTime();
        return getStartDateTime().orElse(getEndDateTime().get());
    }
    
    // ================ Setter methods ==============================
    
    public void setName(Name name) {
        this.name = name;
        setLastUpdatedTimeToNow();
    }
    
    public void markAsCompleted() {
        this.isCompleted = true;
        setLastUpdatedTimeToNow();
    }
    
    public void markAsUncompleted() {
        this.isCompleted = false;
        setLastUpdatedTimeToNow();
    }
    
    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime = startDateTime.orElse(null);
        setLastUpdatedTimeToNow();
    }
    
    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime = endDateTime.orElse(null);
        setLastUpdatedTimeToNow();
    }

    public void setLastUpdatedTime(LocalDateTime updatedTime) {
        this.lastUpdatedTime = updatedTime;
    }

    public void setLastUpdatedTimeToNow() {
        this.lastUpdatedTime = LocalDateTime.now();
    }

    // ================ Other methods ==============================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int compareTo(Task other) {
        int comparedCompletionStatus = compareCompletionStatus(other);
        if (comparedCompletionStatus != 0) {
            return comparedCompletionStatus;
        }

        int comparedTime = compareTime(other);
        if (comparedTime != 0) {
            return comparedTime;
        }

        int comparedLastUpdatedTime = compareLastUpdatedTime(other);
        if (comparedLastUpdatedTime != 0) {
            return comparedLastUpdatedTime;
        }
        
        return compareName(other);
    }

    public int compareCompletionStatus(Task other) {
        return Boolean.compare(this.isCompleted(), other.isCompleted);
    }

    public int compareTime(Task other) {
        if (this.hasTime() && other.hasTime()) {
            return this.getTaskTime().compareTo(other.getTaskTime());
        } else if (this.hasTime()) {
            return -1;
        } else if (other.hasTime()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int compareLastUpdatedTime(Task other) {
        // to fix erratic behavior for logic manager test
        long seconds = ChronoUnit.SECONDS.between(this.getLastUpdatedTime(), other.getLastUpdatedTime());
        if (Math.abs(seconds) < 2) {
            return 0;
        }
        return other.getLastUpdatedTime().compareTo(this.getLastUpdatedTime());
    }

    public int compareName(Task other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, isCompleted, startDateTime, endDateTime);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
