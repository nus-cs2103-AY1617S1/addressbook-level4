package seedu.savvytasker.logic.commands.models;

import java.util.Date;

import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * Represents a model for use with the modify command.
 */
public class ModifyCommandModel extends CommandModel {
    
    public final static int UNINITIALIZED_NR_RECURRENCE_VALUE = -1;
    
    private int index;
    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private PriorityLevel priority;
    private RecurrenceType recurringType;
    private Integer numberOfRecurrence;
    private String category;
    private String description;
    private Boolean isArchived;
    
    public ModifyCommandModel(int index, String taskName, Date startDateTime, Date endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, String category, 
            String description, Boolean isArchived) {
        this.index = index;
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.priority = priority;
        this.recurringType = recurringType;
        this.numberOfRecurrence = numberOfRecurrence;
        this.category = category;
        this.description = description;
        this.isArchived = isArchived;
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

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public RecurrenceType getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(RecurrenceType recurringType) {
        this.recurringType = recurringType;
    }

    public Integer getNumberOfRecurrence() {
        return numberOfRecurrence;
    }

    public void setNumberOfRecurrence(Integer numberOfRecurrence) {
        this.numberOfRecurrence = numberOfRecurrence;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }
}
