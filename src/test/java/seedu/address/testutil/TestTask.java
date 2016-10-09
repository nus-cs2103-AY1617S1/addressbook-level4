package seedu.address.testutil;

import java.util.Date;
import seedu.address.model.person.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {
    
    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private int priority;
    private int recurringType;
    private int numberOfRecurrence;
    private int category;
    private String description;

    public TestTask() {
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public Date getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Date getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public int getRecurringType() {
        return recurringType;
    }

    @Override
    public int getNumberOfRecurrence() {
        return numberOfRecurrence;
    }

    @Override
    public int getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setRecurringType(int recurringType) {
        this.recurringType = recurringType;
    }

    public void setNumberOfRecurrence(int numberOfRecurrence) {
        this.numberOfRecurrence = numberOfRecurrence;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskName());
        return sb.toString();
    }
}
