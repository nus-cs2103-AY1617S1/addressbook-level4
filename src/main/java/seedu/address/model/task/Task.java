package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.exceptions.TaskAlreadyCompletedException;

import java.util.Objects;

/**
 * Represents a Task in the task list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Complete complete;
    
    private Deadline deadline;
    private Period period;
    private Recurrence deadlineRecurrence;
    private Recurrence periodRecurrence;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Complete complete, Deadline deadline, Period period, Recurrence deadlineRecurrence,
            Recurrence periodRecurrence, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, complete, deadline, period, deadlineRecurrence, periodRecurrence, tags);
        this.name = name;
        this.complete = complete;
        this.deadline = deadline;
        this.period = period;
        this.deadlineRecurrence = deadlineRecurrence;
        this.periodRecurrence = periodRecurrence;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getComplete(), source.getDeadline(), source.getPeriod(),
                source.getDeadlineRecurrence(), source.getPeriodRecurrence(), source.getTags());
    }
    
    /**
     * Convert an uncompleted task to completed.
     * 
     * @param taskToConvert to completed
     * @return the same task but with completed = true
     * @throws TaskAlreadyCompletedException if task is already completed
     */
    public static Task convertToComplete(ReadOnlyTask taskToConvert) throws TaskAlreadyCompletedException {
        if (taskToConvert.getComplete().isCompleted() == true) {
            throw new TaskAlreadyCompletedException();
        }
        
        return new Task(taskToConvert.getName(),
                new Complete(true),
                taskToConvert.getDeadline(),
                taskToConvert.getPeriod(),
                taskToConvert.getDeadlineRecurrence(),
                taskToConvert.getPeriodRecurrence(),
                taskToConvert.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
        return Objects.hash(name, complete, deadline, period, deadlineRecurrence, periodRecurrence, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public Complete getComplete() {
        return complete;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public Period getPeriod() {
        return period;
    }

    @Override
    public Recurrence getDeadlineRecurrence() {
        return deadlineRecurrence;
    }

    @Override
    public Recurrence getPeriodRecurrence() {
        return periodRecurrence;
    }

}
