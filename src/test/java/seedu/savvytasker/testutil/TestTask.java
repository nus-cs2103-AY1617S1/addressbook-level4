package seedu.savvytasker.testutil;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.savvytasker.model.task.PriorityLevel;
import seedu.savvytasker.model.task.ReadOnlyTask;
import seedu.savvytasker.model.task.RecurrenceType;

//@@author A0139915W
/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {
    
    private int id;
    private int groupId;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getGroupId() {
        return groupId;
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
    
    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HHmm");
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskName());
        if (startDateTime != null) {
            sb.append(" s/ ").append(sdf.format(startDateTime));
        }
        if (endDateTime != null) {
            sb.append(" e/ ").append(sdf.format(endDateTime));
        }
        if (location != null && !location.isEmpty()) {
            sb.append(" l/ ").append(location);
        }
        if (priority != null && priority != PriorityLevel.Medium) {
            // p/ defaults to medium, if set to medium, take as non-existent
            sb.append(" p/ ").append(priority.toString());
        }
        if (recurringType != null && recurringType != RecurrenceType.None) {
            // r/ defaults to none, if set to none, take as non-existent
            sb.append(" r/ ").append(recurringType.toString());
        }
        if (category != null && !category.isEmpty()) {
            sb.append(" c/ ").append(category);
        }
        if (description != null && !description.isEmpty()) {
            sb.append(" d/ ").append(description);
        }
        return sb.toString();
    }
}
//@@author
