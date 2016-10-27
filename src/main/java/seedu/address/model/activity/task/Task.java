package seedu.address.model.activity.task;

import java.util.Calendar;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;

//@@author A0125680H
public class Task extends Activity implements ReadOnlyTask {

    private DueDate duedate;
    private Priority priority;
    private boolean isCompleted;
    
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, DueDate dueDate, Priority priority, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);
        
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
        } else if(!isCompleted && passedDueDate()){
            return "Passed Due Date";
        }
        
        return "";  
    }
    
    public boolean passedDueDate() {
        if(duedate.value == null) {
        	return false;        	
        } else if(duedate.value.before(Calendar.getInstance())) {
            return true;       
        }
        
        return false;
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
