package seedu.savvytasker.logic.commands.models;

import java.util.Date;

import seedu.savvytasker.logic.parser.DateParser.InferredDate;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * Represents a model for use with the add command.
 * @author A0139915W
 */
public class AddCommandModel extends CommandModel {
    
    private int id;
    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private PriorityLevel priority;
    private RecurrenceType recurringType;
    private Integer numberOfRecurrence;
    private String category;
    private String description;
    
    public AddCommandModel(int id, String taskName, InferredDate startDateTime, InferredDate endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, String category, 
            String description) {
        this.id = id;
        this.taskName = taskName;
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        this.location = location;
        this.priority = priority;
        this.recurringType = recurringType;
        this.numberOfRecurrence = numberOfRecurrence;
        this.category = category;
        this.description = description;
    }
    
    public AddCommandModel(String taskName) {
        this.taskName = taskName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setStartDateTime(InferredDate startDateTime) {
        // TODO: Logic for parsing InferredDate to Date
        this.startDateTime = null;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(InferredDate endDateTime) {
        // TODO: Logic for parsing InferredDate to Date
        this.endDateTime = null;
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
}
