package seedu.oneline.model.task;

import java.util.HashMap;
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
public class Task implements ReadOnlyTask, Comparable<Task> {

    private final TaskName name;
    private final TaskTime startTime;
    private final TaskTime endTime;
    private final TaskTime deadline;
    private final TaskRecurrence recurrence;
    private final Tag tag;
    private final boolean isCompleted;

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
     * @throws IllegalValueException 
     * 
     */    

    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, Tag tag) throws IllegalValueException {
        this(name, startTime, endTime, deadline, recurrence, tag, false);
    }

    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, Tag tag, boolean isCompleted) throws IllegalValueException {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, recurrence, tag);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.recurrence = recurrence;
        this.tag = tag;
        this.isCompleted = isCompleted;
        checkValidTaskTime(this);
    }

    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) throws IllegalValueException {
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

    @Override
    public boolean equals(Object other) {
        return other == this ||
                (other instanceof ReadOnlyTask &&
                this.isSameStateAs((ReadOnlyTask) other));
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

    //@@author A0140156R
    public Task update(Map<TaskField, String> fields) throws IllegalValueException {
        ReadOnlyTask oldTask = this;
        
        TaskName newName = oldTask.getName();
        TaskTime newStartTime = oldTask.getStartTime();
        TaskTime newEndTime = oldTask.getEndTime();
        TaskTime newDeadline = oldTask.getDeadline();
        TaskRecurrence newRecurrence = oldTask.getRecurrence();
        Tag newTag = oldTask.getTag();
        boolean newCompleted = oldTask.isCompleted();

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
            case IS_DONE:
                newCompleted = Boolean.parseBoolean(entry.getValue());
                break;
            }
        }
        Task newTask = new Task(newName, newStartTime, newEndTime, newDeadline, newRecurrence, newTag, newCompleted);
        System.out.println("C: " + newTask.isCompleted());
        return newTask;
    }
    
    //@@author A0121657H
    /**
     * Copies data over to new Task and marks it as done
     * @param taskToDone
     * @return
     */
    @Override
    public Task markDone() {
        return markDone(true);
    }
    
    /**
     * Copies data over to new Task and marks it as not done
     * @param taskToDone
     * @return
     */
    @Override
    public Task markUndone() {
        return markDone(false);
    }
    /**
     * Copies data over to new Task and marks it as done
     * @param taskToDone
     * @return
     */
    private Task markDone(boolean isDone) {
        Map<TaskField, String> fields = new HashMap<TaskField, String>();
        fields.put(TaskField.IS_DONE, String.valueOf(isDone));
        try {
            return this.update(fields);
        } catch (IllegalValueException e) {
            assert false;
            return null;
        }
    }
    
    // @@author A0140156R
    
    private static void checkValidTaskTime(Task t) throws IllegalValueException {
      boolean haveStartTime = t.getStartTime().isValid();
      boolean haveEndTime = t.getEndTime().isValid();
      boolean haveDeadline = t.getDeadline().isValid();
      if (!haveStartTime && !haveEndTime) {
          // floating tasks or deadline tasks
          return;
      } else if (haveStartTime && haveEndTime && !haveDeadline) {
          // event task
          return;
      } else if (!haveDeadline) {
          // there is a start/end time, but no end/start time
          if (haveStartTime) {
              throw new IllegalValueException("End time for event is not specified.");
          } else {
              throw new IllegalValueException("Start time for event is not specified.");
          }
      } else {
          throw new IllegalValueException("If a task has a deadline, it should not have a start or end time specified.");
      }
  }
    //@@author

    //@@author A0138848M
    /**
     * Returns a new Task with all fields of the current task duplicated and 
     * with its tag updated to newTag
     * 
     * @param newTag
     * @return task with updated tag
     */
    @Override
    public Task updateTag(Tag newTag) {
        assert newTag != null;
        
        ReadOnlyTask oldTask = this;
        
        TaskName newName = oldTask.getName();
        TaskTime newStartTime = oldTask.getStartTime();
        TaskTime newEndTime = oldTask.getEndTime();
        TaskTime newDeadline = oldTask.getDeadline();
        TaskRecurrence newRecurrence = oldTask.getRecurrence();

        Task newTask = null;
        try {
            newTask = new Task(newName, newStartTime, newEndTime, newDeadline, newRecurrence, newTag, false);
        } catch (IllegalValueException e) {
            assert false;
        }
        return newTask;
    }
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
        int result;
        if (this.isEvent()){
            result = o.isEvent() 
                    ? this.endTime.compareTo(o.endTime) 
                    : this.endTime.compareTo(o.deadline);
        } else {
            result = o.isEvent() 
                    ? this.deadline.compareTo(o.endTime) 
                    : this.deadline.compareTo(o.deadline);
        }
        return result == 0 
                ? this.name.compareTo(o.name)
                : result;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted; 
    }
}
