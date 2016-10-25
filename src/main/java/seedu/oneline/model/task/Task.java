package seedu.oneline.model.task;

import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.commons.util.CollectionUtil;
import seedu.oneline.logic.commands.CommandResult;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;
    
    private Tag tag;

    /**
     * Every field must be present and not null.
     */    
    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, Tag tag) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, recurrence, tag);
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
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getDeadline(), source.getRecurrence(), source.getTag());
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
    
    public Task update(Map<TaskField, String> fields) throws IllegalValueException {
        ReadOnlyTask oldTask = this;
        
        TaskName newName = oldTask.getName();
        TaskTime newStartTime = oldTask.getStartTime();
        TaskTime newEndTime = oldTask.getEndTime();
        TaskTime newDeadline = oldTask.getDeadline();
        TaskRecurrence newRecurrence = oldTask.getRecurrence();
        Tag newTag = oldTask.getTag();

        for (Entry<TaskField, String> entry : fields.entrySet()) {
            switch (entry.getKey()) {
            case NAME:
                newName = new TaskName(entry.getValue());
                break;
            case START_TIME:
                newStartTime = new TaskTime(entry.getValue());
                break;
            case END_TIME:
                newEndTime = new TaskTime(entry.getValue());
                break;
            case DEADLINE:
                newDeadline = new TaskTime(entry.getValue());
                break;
            case RECURRENCE:
                newRecurrence = new TaskRecurrence(entry.getValue());
                break;
            case TAG:
                newTag = Tag.getTag(entry.getValue());
                break;
            }
        }
        Task newTask = new Task(newName, newStartTime, newEndTime, newDeadline, newRecurrence, newTag);
        return newTask;
    }

}
