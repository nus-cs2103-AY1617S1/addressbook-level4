package seedu.agendum.testutil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import seedu.agendum.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

    private static final int UPCOMING_DAYS_THRESHOLD = 7;

    private Name name;
    private boolean isCompleted;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime lastUpdatedTime;

    public TestTask() {
        isCompleted = false;
        startDateTime = null;
        endDateTime = null;
        setLastUpdatedTimeToNow();
    }

    /**
     * Copy constructor.
     */
    public TestTask(TestTask other) {
        this.name = other.name;
        this.isCompleted = other.isCompleted;
        this.startDateTime = other.startDateTime;
        this.endDateTime = other.endDateTime;
        this.lastUpdatedTime = other.getLastUpdatedTime();
    }

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

    public void setLastUpdatedTimeToNow() {
        this.lastUpdatedTime = LocalDateTime.now();
    }

    public void setLastUpdatedTime(LocalDateTime updatedTime) {
        this.lastUpdatedTime = updatedTime;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isUpcoming() {
        return  !isCompleted() && hasTime() && getTaskTime().isBefore(
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
     * Return the (earlier) time associated with the task
     */
    private LocalDateTime getTaskTime() {
        assert hasTime();
        return getStartDateTime().orElse(getEndDateTime().get());
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        return sb.toString();
    }

    public int compareTo(TestTask other) {
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

    public int compareCompletionStatus(TestTask other) {
        return Boolean.compare(this.isCompleted(), other.isCompleted());
    }

    public int compareTime(TestTask other) {
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

    public int compareLastUpdatedTime(TestTask other) {
        // to fix erratic behavior for logic manager tests
        long seconds = ChronoUnit.SECONDS.between(this.getLastUpdatedTime(), other.getLastUpdatedTime());
        if (Math.abs(seconds) < 2) {
            return 0;
        }
        return other.getLastUpdatedTime().compareTo(this.getLastUpdatedTime());
    }

    public int compareName(TestTask other) {
        return this.getName().toString().compareTo(other.getName().toString());
    }


}
