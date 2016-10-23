package seedu.oneline.model.task;

import java.util.Objects;

import seedu.oneline.commons.util.CollectionUtil;
import seedu.oneline.model.tag.Tag;
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
    
    private boolean isCompleted;
    private Tag tag;

    /**
     * Every field must be present and not null.
     * 
     * Definitions:
     * Event: 
     *  A task with start time and end time.
     *  If a task has a start time, it is guaranteed to have an end time
     * Task w/ Deadline: 
     *  A task with a non empty deadline
     *  If a task has an end time, its deadline is automatically set to its end time
     * Floating task: 
     *  A task without a deadline
     * 
     */    

    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, Tag tag, boolean isCompleted) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, recurrence, tag);
        this.setCompleted(isCompleted);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.recurrence = recurrence;
        this.tag = tag;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getDeadline(), source.getRecurrence(), source.getTag(), source.isCompleted());
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
    public Tag getTag() {
        return tag;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    
    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTag(Tag replacement) {
        this.tag = replacement;
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
        return Objects.hash(name, startTime, endTime, deadline, recurrence, tag);
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
    
    /**
     * Returns true if task is an event
     * 
     * event task is defined as a task with a start time and end time
     * 
     * @return true if task is an event
     */
    public boolean isEvent() {
        return startTime.isValid() && endTime.isValid();
    }

    /**
     * Returns true if task has a deadline
     * 
     * Note that events and tasks with deadline will have a deadline.
     * Event tasks automatically has its endTime set as the deadline.
     * 
     * @return true if task has a deadline
     */
    public boolean hasDeadline() {
        return deadline.isValid();
    }

    /**
     * Compares by deadline, then compares by name
     */
    @Override
    public int compareTo(Task o) {
        assert o != null;
        if (deadline.compareTo(o.deadline) == 0){
            return name.compareTo(o.name);
        } else {
            return deadline.compareTo(o.deadline);
        }
    }
}
