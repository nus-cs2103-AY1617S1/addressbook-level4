package seedu.address.logic.commands.models;

import java.util.Date;

/**
 * Represents a model for use with the modify command.
 */
public class ModifyCommandModel extends CommandModel {
    
    private int index;
    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private int priority;
    private int recurringType;
    private int numberOfRecurrence;
    private int category;
    private String description;
    
    public ModifyCommandModel(int index, String taskName, Date startDateTime, Date endDateTime, String location,
            int priority, int recurringType, int numberOfRecurrence, int category, String description) {
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.priority = priority;
        this.recurringType = recurringType;
        this.numberOfRecurrence = numberOfRecurrence;
        this.category = category;
        this.description = description;
    }
    
    public ModifyCommandModel(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(int recurringType) {
        this.recurringType = recurringType;
    }

    public int getNumberOfRecurrence() {
        return numberOfRecurrence;
    }

    public void setNumberOfRecurrence(int numberOfRecurrence) {
        this.numberOfRecurrence = numberOfRecurrence;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
