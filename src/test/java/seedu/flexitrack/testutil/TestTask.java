package seedu.flexitrack.testutil;

import java.util.Objects;

import seedu.flexitrack.commons.util.CollectionUtil;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.model.task.Name;
import seedu.flexitrack.model.task.ReadOnlyTask;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private DateTimeInfo dueDate;
    private DateTimeInfo startTime;
    private DateTimeInfo endTime;
    private boolean isEvent;
    private boolean isTask;
    private boolean isDone = false;
    private boolean isBlock = false;

    /**
     * Every field must be present and not null.
     */
    public TestTask(Name name, DateTimeInfo dueDate, DateTimeInfo startTime, DateTimeInfo endTime) {
        assert !CollectionUtil.isAnyNull(name);
        this.name = name;
        this.dueDate = dueDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isTask = dueDate.isDateNull() ? false : true;
        this.isEvent = startTime.isDateNull() ? false : true;
        this.isDone = name.getIsDone();
        this.isBlock = name.getIsBlock();
    }

    public TestTask() {

    }

    /**
     * Copy constructor.
     */
    public TestTask(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate(), source.getStartTime(), source.getEndTime());
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDueDate(DateTimeInfo date) {
        this.dueDate = date;
    }

    public void setStartTime(DateTimeInfo date) {
        this.startTime = date;
    }

    public void setEndTime(DateTimeInfo date) {
        this.endTime = date;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public boolean getIsTask() {
        return isTask;
    }

    @Override
    public boolean getIsEvent() {
        return isEvent;
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }
    
    @Override
    public boolean getIsBlock() {
        return isBlock;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }
    
    public void setIsBlock(boolean isBlock) {
        name.setBlock();
        this.isBlock = isBlock;
    }

    @Override
    public DateTimeInfo getDueDate() {
        return dueDate;
    }

    @Override
    public DateTimeInfo getStartTime() {
        return startTime;
    }

    @Override
    public DateTimeInfo getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                        && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, dueDate, startTime, endTime, isTask, isEvent);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    //@@author A0138455Y
    public String getBlockCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("block " + this.getName().toString() + " ");
        sb.append("from/" + this.getStartTime().toString() + " ");
        sb.append("to/" + this.getEndTime().toString() + " ");
        return sb.toString();
    }
    //@@author
    
    //@@author A0127686R
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().toString() + " ");
        if (getIsTask()) {
            sb.append("by/" + this.getDueDate().toString() + " ");
        } else if (getIsEvent()) {
            sb.append("from/" + this.getStartTime().toString() + " ");
            sb.append("to/" + this.getEndTime().toString() + " ");
        }
        return sb.toString();
    }

    public static String getMarkCommand(int mark) {
        StringBuilder sb = new StringBuilder();
        sb.append("mark " + mark);
        return sb.toString();
    }

    //@@author 
    public static String getUnMarkCommand(int taskToUnMark) {
        StringBuilder sb = new StringBuilder();
        sb.append("unmark " + taskToUnMark);
        return sb.toString();
    }

}
