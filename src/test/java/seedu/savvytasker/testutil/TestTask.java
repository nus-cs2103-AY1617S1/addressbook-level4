package seedu.savvytasker.testutil;

import java.util.Date;

import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {
    
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

    public TestTask() {
        // sets initial default values
        this.priority = PriorityLevel.Medium;
        this.recurringType = RecurrenceType.None;
        this.numberOfRecurrence = 0;
    }

    public int getId() {
        return id;
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
    
    @Override
    public boolean isMarked() {
        return isArchived(); // all marked tasks are archived
    }

    @Override
    public boolean isArchived() {
        return isArchived;
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
    
    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
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
