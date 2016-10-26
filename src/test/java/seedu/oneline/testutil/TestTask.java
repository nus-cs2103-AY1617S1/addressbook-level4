package seedu.oneline.testutil;

import java.util.Map;
import java.util.Map.Entry;

import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;
    private Tag tag;
    private boolean isCompleted; 

    public TestTask() {
    }
    
    public TestTask(ReadOnlyTask task) {
        this.name = task.getName();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.deadline = task.getDeadline();
        this.recurrence = task.getRecurrence();
        this.tag = task.getTag();
        this.isCompleted = task.isCompleted();
    }
    
    public void setName(TaskName name) {
        this.name = name;
    }
    
    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }
    
    public void setDeadline(TaskTime deadline) {
        this.deadline = deadline;
    }

    public void setRecurrence(TaskRecurrence recurrence) {
        this.recurrence = recurrence;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted; 
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
    public boolean isCompleted() {
        return isCompleted; 
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().toString() + " ");
        sb.append(".from " + this.getStartTime().toString() + " ");
        sb.append(".to " + this.getEndTime().toString() + " ");
        sb.append(".due " + this.getDeadline().toString() + " ");
        sb.append(".every " + this.getRecurrence().toString() + " ");
        sb.append("#" + this.getTag().getTagName());
        return sb.toString();
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

    //@@author A0138848M
    public boolean isFloating() {
        return !startTime.isValid() && !endTime.isValid() && !deadline.isValid();
    }
    
    public boolean isEvent() {
        return startTime.isValid() && endTime.isValid();
    }

    public boolean hasDeadline() {
        return deadline.isValid();
    }
    
    /**
     * Compares by deadline, then compares by name
     */
    @Override
    public int compareTo(TestTask o) {
        if (deadline.compareTo(o.deadline) == 0){
            return name.compareTo(o.name);
        } else {
            return deadline.compareTo(o.deadline);
        }
    }
}
