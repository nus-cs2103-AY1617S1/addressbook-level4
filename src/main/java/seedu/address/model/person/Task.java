package seedu.address.model.person;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Task in the task list.
 */
public class Task implements ReadOnlyTask {

    private String taskName;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private int priority;
    private int recurringType;
    private int numberOfRecurrence;
    private int category;
    private String description;

    public Task(String taskName, Date startDateTime, Date endDateTime, String location,
            int priority, int recurringType, int numberOfRecurrence, int category, String description) {
        //assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
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
    
    public Task(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getStartDateTime(), source.getEndDateTime(), 
                source.getLocation(), source.getPriority(), source.getRecurringType(),
                source.getNumberOfRecurrence(), source.getCategory(), source.getDescription());
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
