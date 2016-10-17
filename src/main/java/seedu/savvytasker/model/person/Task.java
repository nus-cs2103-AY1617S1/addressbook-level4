package seedu.savvytasker.model.person;

import java.util.Date;
import java.util.Objects;

import seedu.savvytasker.logic.commands.models.ModifyCommandModel;
import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * Represents a Task in the task list.
 */
public class Task implements ReadOnlyTask {

    private int id;
    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private PriorityLevel priority;
    private RecurrenceType recurringType;
    private int numberOfRecurrence;
    private String category;
    private String description;
    private boolean isArchived;

    public Task(int id, String taskName, Date startDateTime, Date endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, int numberOfRecurrence, 
            String category, String description, boolean isArchived) {
        this.id = id;
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
    
    public Task(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getId(), source.getTaskName(), source.getStartDateTime(), 
                source.getEndDateTime(), source.getLocation(), source.getPriority(), 
                source.getRecurringType(), source.getNumberOfRecurrence(), 
                source.getCategory(), source.getDescription(), source.isArchived());
    }

    /**
     * Copy and modify constructor
     */
    public Task(ReadOnlyTask source, ModifyCommandModel commandModel) {
        this(source.getId(), source.getTaskName(), source.getStartDateTime(), 
                source.getEndDateTime(), source.getLocation(), source.getPriority(), 
                source.getRecurringType(), source.getNumberOfRecurrence(), 
                source.getCategory(), source.getDescription(), source.isArchived());
        
        //this.id should follow that of the source.
        this.taskName = commandModel.getTaskName() == null ? this.taskName : commandModel.getTaskName();
        this.startDateTime = commandModel.getStartDateTime() == null ? this.startDateTime : commandModel.getStartDateTime();
        this.endDateTime = commandModel.getEndDateTime() == null ? this.endDateTime : commandModel.getEndDateTime();
        this.location = commandModel.getLocation() == null ? this.location : commandModel.getLocation();
        this.priority = commandModel.getPriority() == null ? this.priority : commandModel.getPriority();
        this.recurringType = commandModel.getRecurringType() == null ? this.recurringType : commandModel.getRecurringType();
        this.numberOfRecurrence = commandModel.getNumberOfRecurrence() == null ? this.numberOfRecurrence : commandModel.getNumberOfRecurrence().intValue();
        this.category = commandModel.getCategory() == null ? this.category : commandModel.getCategory();
        this.description = commandModel.getDescription() == null ? this.description : commandModel.getDescription();
        this.isArchived = commandModel.getIsArchived() == null ? this.isArchived : commandModel.getIsArchived().booleanValue();
    }

    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public boolean isMarked() {
        return isArchived(); // all marked tasks are archived
    }
    
    @Override
    public boolean isArchived() {
        return this.isArchived;
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
    public PriorityLevel getPriority() {
        return priority;
    }

    @Override
    public RecurrenceType getRecurringType() {
        return recurringType;
    }

    @Override
    public int getNumberOfRecurrence() {
        return numberOfRecurrence;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    public void setId(int id) {
        this.id = id;
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

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public void setRecurringType(RecurrenceType recurringType) {
        this.recurringType = recurringType;
    }

    public void setNumberOfRecurrence(int numberOfRecurrence) {
        this.numberOfRecurrence = numberOfRecurrence;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Marks the task as complete, also moves this task from the active list 
     * to the archived list.
     */
    public void Mark() { 
        if (!isMarked()) {
            this.isArchived = true;
        }
    }
    
    /**
     * Unmarks the task as complete, also moves this task from the archived list 
     * to the active list.
     */
    public void Unmark() {
        if (isMarked()) {
            this.isArchived = false;
        }
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
        return Objects.hash(taskName);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
