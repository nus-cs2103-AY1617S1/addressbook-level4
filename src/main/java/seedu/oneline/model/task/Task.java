package seedu.oneline.model.task;

import java.util.Objects;

import seedu.oneline.commons.util.CollectionUtil;
import seedu.oneline.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;
    
    private UniqueTagList tags;
    private boolean isCompleted;

    /**
     * Every field must be present and not null.
     */    
    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, recurrence, tags);
        this.setCompleted(false);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.recurrence = recurrence;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getDeadline(), source.getRecurrence(), source.getTags());
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskTime getDeadline() {
        return deadline;
    }

    @Override
    public TaskRecurrence getRecurrence() {
        return recurrence;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Replaces this person's tags with the tags in the argument tag list.
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
        return Objects.hash(name, startTime, endTime, deadline, recurrence, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    //@@author A0138848M
    /**
     * Returns true if task is floating
     * 
     * floating task is defined as a task without a start/end time or a deadline
     * 
     * @return true if task is floating
     */
    public boolean isFloating() {
        return !startTime.isValid() && !endTime.isValid() && !deadline.isValid();
    }
    

}
