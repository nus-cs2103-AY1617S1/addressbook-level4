package seedu.agendum.model.task;

import seedu.agendum.commons.util.CollectionUtil;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

//@@author A0133367E
/**
 * Represents a Task in the to do list.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private Name name_;
    private boolean isCompleted_;
    private LocalDateTime startDateTime_;
    private LocalDateTime endDateTime_;
    private LocalDateTime lastUpdatedTime_;
    
    // ================ Constructor methods ==============================

    /**
     * Constructor for a floating task (with no deadline/start time or end time)
     */
    public Task(Name name) {
        assert CollectionUtil.isNotNull(name);
        this.name_ = name;
        this.isCompleted_ = false;
        this.startDateTime_ = null;
        this.endDateTime_ = null;
        setLastUpdatedTimeToNow();
    }
    
    /**
     * Constructor for a task with deadline only
     */
    public Task(Name name, Optional<LocalDateTime> deadline) {
        assert CollectionUtil.isNotNull(name);
        this.name_ = name;
        this.isCompleted_ = false;
        this.startDateTime_ = null;
        this.endDateTime_ = deadline.orElse(null);
        this.setLastUpdatedTimeToNow();
    }
    
    /**
     * Constructor for a task (event) with both a start and end time
     */
    public Task(Name name, Optional<LocalDateTime> startDateTime,
            Optional<LocalDateTime> endDateTime) {
        assert CollectionUtil.isNotNull(name);
        this.name_ = name;
        this.isCompleted_ = false;
        this.startDateTime_ = startDateTime.orElse(null);
        this.endDateTime_ = endDateTime.orElse(null);
        this.setLastUpdatedTimeToNow();
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime());
        if (source.isCompleted()) {
            this.markAsCompleted();
        }
        this.setLastUpdatedTime(source.getLastUpdatedTime());
    }
    
    // ================ Getter methods ==============================

    @Override
    public Name getName() {
        return name_;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted_;
    }

    /**
     * Returns true if a task is uncompleted and has a start/end time
     * that is after the current time but within some threshold amount of days
     */
    @Override
    public boolean isUpcoming() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime thresholdTime = currentTime.plusDays(UPCOMING_DAYS_THRESHOLD);
        boolean isBeforeUpcomingDaysThreshold = getTaskTime().isBefore(thresholdTime);
        boolean isAfterCurrentTime = getTaskTime().isAfter(currentTime);

        return isBeforeUpcomingDaysThreshold && isAfterCurrentTime;
    }

    /**
     * Returns true is a task is uncompleted and has a start/end time
     * that is before the current time
     */
    @Override
    public boolean isOverdue() {
        if (isCompleted()) {
            return false;
        }

        if (!hasTime()) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        boolean isBeforeCurrentTime = getTaskTime().isBefore(currentTime);
                
        return isBeforeCurrentTime;
    }

    /**
     * Returns true if a task has a start time or an end time, false otherwise
     * This must be called to check if comparison of task's time is possible
     */
    @Override
    public boolean hasTime() {
        return getStartDateTime().isPresent() || getEndDateTime().isPresent();
    }

    /**
     * Returns true if the task has a start time and end time, false otherwise.
     */
    @Override
    public boolean isEvent() {
        return getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    /**
     * Returns true if the task has a deadline (i.e. only a end time), false otherwise.
     */
    @Override
    public boolean hasDeadline() {
        return !getStartDateTime().isPresent() && getEndDateTime().isPresent();
    }

    @Override
    public Optional<LocalDateTime> getStartDateTime() {
        return Optional.ofNullable(startDateTime_);
    }

    @Override
    public Optional<LocalDateTime> getEndDateTime() {
        return Optional.ofNullable(endDateTime_);
    }

    /**
     * Returns the time the task is last updated.
     * e.g. created, renamed, rescheduled, marked or unmarked
     */
    @Override
    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime_;
    }

    /**
     * Pre-condition: Task has a start or end time.
     * Returns the start time if present, else returns the end time. 
     */
    private LocalDateTime getTaskTime() {
        assert hasTime();
        return getStartDateTime().orElse(getEndDateTime().get());
    }
    
    // ================ Setter methods ==============================
    
    public void setName(Name name) {
        this.name_ = name;
        setLastUpdatedTimeToNow();
    }
    
    public void markAsCompleted() {
        this.isCompleted_ = true;
        setLastUpdatedTimeToNow();
    }
    
    public void markAsUncompleted() {
        this.isCompleted_ = false;
        setLastUpdatedTimeToNow();
    }
    
    public void setStartDateTime(Optional<LocalDateTime> startDateTime) {
        this.startDateTime_ = startDateTime.orElse(null);
        setLastUpdatedTimeToNow();
    }
    
    public void setEndDateTime(Optional<LocalDateTime> endDateTime) {
        this.endDateTime_ = endDateTime.orElse(null);
        setLastUpdatedTimeToNow();
    }

    public void setLastUpdatedTime(LocalDateTime updatedTime) {
        this.lastUpdatedTime_ = updatedTime;
    }

    public void setLastUpdatedTimeToNow() {
        // nano-seconds is set to 0 for more consistent test results when (un)marking multiple tasks
        this.lastUpdatedTime_ = LocalDateTime.now().withNano(0);
    }

    // ================ Other methods ==============================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    /**
     * Compares the current task with another Task other.
     * The current task is considered to be less than the other task if
     * 1) it is uncompleted and other is completed
     * 2) both tasks are completed but this task has a earlier start/end time associated
     * 3) both tasks are uncompleted but this task has a later updated time
     * 4) both tasks are uncompleted with the same updated time
     *    but this task has a lexicographically smaller name (useful when sorting tasks in testing)
     */
    @Override
    public int compareTo(Task other) {
        int comparedCompletionStatus = compareCompletionStatus(other);
        if (comparedCompletionStatus != 0) {
            return comparedCompletionStatus;
        }

        int comparedTaskTime = compareTaskTime(other);
        if (!isCompleted() && comparedTaskTime != 0) {
            return comparedTaskTime;
        }

        int comparedLastUpdatedTime = compareLastUpdatedTime(other);
        if (comparedLastUpdatedTime != 0) {
            return comparedLastUpdatedTime;
        }
        
        return compareName(other);
    }

    /**
     * Compares the completion status of current task with another Task other.
     * The current task is considered to be less than the other task if
     * it is uncompleted and other is completed
     */
    public int compareCompletionStatus(Task other) {
        return Boolean.compare(this.isCompleted(), other.isCompleted());
    }

    /**
     * Compares the earliest time of the current task with another Task other.
     * The current task is considered to be less than the other task if
     * 1) both tasks have a time associated but this task has a earlier time associated
     * 2) this task has a time associated but the other task does not.
     * Both tasks are equal if they have no time or the same earliest time associated.
     * Time refers to value returned by {@link #getTaskTime()}
     */
    public int compareTaskTime(Task other) {
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

    /**
     * Compares the current task with another Task other.
     * The current task is considered to be less than the other task if
     * it has a later updated time
     */
    public int compareLastUpdatedTime(Task other) {
        return other.getLastUpdatedTime().compareTo(this.getLastUpdatedTime());
    }

    /**
    * Compares the current task with another Task other.
    * The current task is considered to be less than the other task if
    * it has a lexicographically smaller name
    */
    public int compareName(Task other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name_, isCompleted_, startDateTime_, endDateTime_);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
