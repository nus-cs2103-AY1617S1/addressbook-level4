package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;
    private Deadline deadline;
    private Status status;
    private Recurring recurring;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, StartTime startTime, EndTime endTime, Deadline deadline, UniqueTagList tags, Status status, Recurring recurring) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, tags, recurring);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.status = status;
        this.recurring = recurring;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getDeadline(), source.getTags(), source.getStatus(), source.getRecurring());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    // @@author A0147944U
    @Override
    public Recurring getRecurring() {
        return recurring;
    }
    // @@author

    /**
     * Replaces this task tags with the tags in the argument tag list.
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
        return Objects.hash(name, startTime, endTime, deadline, tags, status, recurring);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}