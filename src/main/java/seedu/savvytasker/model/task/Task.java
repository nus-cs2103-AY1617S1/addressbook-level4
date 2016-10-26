package seedu.savvytasker.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.savvytasker.commons.util.SmartDefaultDates;
import seedu.savvytasker.logic.parser.DateParser.InferredDate;

//@@author A0139915W
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

    /**
     * Constructor with smart defaults
     */
    public Task(int id, String taskName, InferredDate startDateTime, InferredDate endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, 
            String category, String description, boolean isArchived) {
        
        SmartDefaultDates sdd = new SmartDefaultDates(startDateTime, endDateTime);
        this.id = id;
        this.taskName = taskName;
        this.startDateTime = sdd.getStartDate();
        this.endDateTime = sdd.getEndDate();
        this.location = location;
        if (priority == null) {
            this.priority = PriorityLevel.Medium;
        } else {
            this.priority = priority;
        }
        if (recurringType == null) {
            this.recurringType = RecurrenceType.None;
        } else {
            this.recurringType = recurringType;
        }
        if (numberOfRecurrence == null) {
            this.numberOfRecurrence = 0;
        } else {
            this.numberOfRecurrence = numberOfRecurrence.intValue();
        }
        this.category = category;
        this.description = description;
        this.isArchived = isArchived;
    }
    
    /**
     * Constructor without smart defaults
     */
    public Task(int id, String taskName, Date startDateTime, Date endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, 
            String category, String description, boolean isArchived) {
        
        this.id = id;
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        if (priority == null) {
            this.priority = PriorityLevel.Medium;
        } else {
            this.priority = priority;
        }
        if (recurringType == null) {
            this.recurringType = RecurrenceType.None;
        } else {
            this.recurringType = recurringType;
        }
        if (numberOfRecurrence == null) {
            this.numberOfRecurrence = 0;
        } else {
            this.numberOfRecurrence = numberOfRecurrence.intValue();
        }
        this.category = category;
        this.description = description;
        this.isArchived = isArchived;
    }
    
    public Task(String taskName) {
        this.taskName = taskName;
        // sets initial default values
        this.priority = PriorityLevel.Medium;
        this.recurringType = RecurrenceType.None;
        this.numberOfRecurrence = 0;
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
    public Task(ReadOnlyTask source, String taskName, InferredDate startDateTime, InferredDate endDateTime, String location,
            PriorityLevel priority, RecurrenceType recurringType, Integer numberOfRecurrence, String category, 
            String description) {
        this(source.getId(), source.getTaskName(), source.getStartDateTime(), 
                source.getEndDateTime(), source.getLocation(), source.getPriority(), 
                source.getRecurringType(), source.getNumberOfRecurrence(), 
                source.getCategory(), source.getDescription(), source.isArchived());
        
        //this.id should follow that of the source.
        //this.isArchived should follow that of the source.
        this.taskName = taskName == null ? this.taskName : taskName;
        setStartDate(startDateTime);
        setEndDate(endDateTime);
        this.location = location == null ? this.location : location;
        this.priority = priority == null ? this.priority : priority;
        this.recurringType = recurringType == null ? this.recurringType : recurringType;
        this.numberOfRecurrence = numberOfRecurrence == null ? this.numberOfRecurrence : numberOfRecurrence.intValue();
        this.category = category == null ? this.category : category;
        this.description = description == null ? this.description : description;
    }
    
    private void setStartDate(InferredDate inferredDate) {
        if (inferredDate == null) {
            // user didn't specify s/
            // keep existing start date
        } else {
            if (inferredDate.isDateInferred() && inferredDate.isTimeInferred()) {
                // user specified s/ but with nothing tagged to it
                // remove existing start date
                this.startDateTime = null;
            } else {
                // user specified s/ with something tagged to it
                // update existing start date
                SmartDefaultDates sdd = new SmartDefaultDates(null, null);
                this.startDateTime = sdd.getStart(inferredDate);
            }
        }
    }
    
    private void setEndDate(InferredDate inferredDate) {
        if (inferredDate == null) {
            // user didn't specify e/
            // keep existing end date
        } else {
            if (inferredDate.isDateInferred() && inferredDate.isTimeInferred()) {
                // user specified e/ but with nothing tagged to it
                // remove existing end date
                this.endDateTime = null;
            } else {
                // user specified e/ with something tagged to it
                // update existing end date
                SmartDefaultDates sdd = new SmartDefaultDates(null, null);
                this.endDateTime = sdd.getStart(inferredDate);
            }
        }
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
    
    public void setArchived(boolean isArchived) {
        if (isArchived) mark();
        else unmark();
    }
    
    /**
     * Marks the task as complete, also moves this task from the active list 
     * to the archived list.
     */
    public void mark() { 
        if (!isMarked()) {
            this.isArchived = true;
        }
    }
    
    /**
     * Unmarks the task as complete, also moves this task from the archived list 
     * to the active list.
     */
    public void unmark() {
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
//@@author
