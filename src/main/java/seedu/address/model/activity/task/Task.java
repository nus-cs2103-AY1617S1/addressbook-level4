package seedu.address.model.activity.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;
//@@author A0131813R
public class Task extends Activity implements ReadOnlyTask {

    private DueDate duedate;
    private Priority priority;
    private boolean isCompleted;
    
    private static int DAYS_WARNING = -3;
    
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate dueDate, Priority priority, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);
        
//        assert !CollectionUtil.isAnyNull(dueDate, priority);
        this.duedate = dueDate;
        this.priority = priority;
        this.isCompleted = false;
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDueDate(), source.getPriority(), source.getReminder(), source.getTags());
    }
    
    @Override
    public DueDate getDueDate() {
        return duedate;
    }
    
    public void setDueDate(DueDate duedate) {
        this.duedate = duedate;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }



    @Override
    public boolean getCompletionStatus() {
        return isCompleted;
    }
    
    public void setCompletionStatus(boolean isComplete) {
        this.isCompleted = isComplete;
    }
    
    @Override
    public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } else if (!isCompleted && this.isDueDateApproaching()) {
            return "Task Deadline Approaching";
        } else if(!isCompleted && this.hasPassedDueDate()){
            return "Task Overdue!";
        }
        
        return "";  
    }
    
    /**
     * Checks if the due date is approaching and returns true if so.
     * @return true if the current time is a certain number of days before the due date (default 3).
     */
    @Override
    public boolean isDueDateApproaching() {
        if(duedate.getCalendarValue() == null) {
            return false;           
        } else {
            Calendar cal = Calendar.getInstance();
            Date now = cal.getTime();
            cal.add(Calendar.DAY_OF_MONTH, DAYS_WARNING);
            Date warningDate = cal.getTime();
            return warningDate.before(now)
                    && duedate.getCalendarValue().getTime().after(now);       
        }
    }
    
    /**
     * Returns true if the task is overdue.
     * @return true if the current time is after the task's due date.
     */
    @Override
    public boolean hasPassedDueDate() {
        if(duedate.getCalendarValue() == null) {
        	return false;        	
        } else {
            Date now = Calendar.getInstance().getTime();
            return duedate.getCalendarValue().getTime().before(now);       
        }
    }

    
    @Override
    public boolean equals(Object other) {
        if (this == null || other == null) {
            return !(this == null ^ other == null);
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            return other == this // short circuit if same object
                    || (other instanceof ReadOnlyActivity // instanceof handles nulls
                    && ((Task) other).getName().equals(this.getName()) // state checks here onwards
                    && ((Task) other).getDueDate().equals(this.getDueDate())
                    && ((Task) other).getPriority().equals(this.getPriority())
                    && ((Task) other).getReminder().equals(this.getReminder()));
        }
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, duedate, priority, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
