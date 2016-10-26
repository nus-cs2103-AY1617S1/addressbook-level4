package seedu.address.testutil;

import java.util.Calendar;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.util.DateUtil;

/**
 * A mutable Task object. For testing only.
 *
 * @author Bangwu
 */

public class TestTask extends TestActivity implements ReadOnlyTask{

    private DueDate duedate;
    private Priority priority;
	
    public TestTask() throws IllegalValueException {
    	super();
    	this.duedate = new DueDate("");
    	this.priority = new Priority("");
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
	public boolean passedDueDate() {
		return false;
	}

	@Override
	public String toStringCompletionStatus() {
        if(isCompleted) {
            return "Completed";
        } 
            return ""; 
	}
    
    //TestTask specific methods
    
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        DateUtil dUtil = new DateUtil();
        String dateFormat = "EEE, MMM d, yyyy h:mm a";
        
        sb.append("add " + this.getName().fullName + " ");
        
        if (getDueDate().value != null) {
        sb.append("d/" + dUtil.outputDateTimeAsString(this.getDueDate().getCalendarValue(), dateFormat) + " ");     
        }
        
        
        if (!getPriority().value.equals("")) {
        sb.append("p/" + this.getPriority().value + " ");   
        }
        
        if (getReminder().value != null) {
        sb.append("r/" + dUtil.outputDateTimeAsString(this.getReminder().getCalendarValue(), dateFormat) + " ");
        }

        
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString().trim();
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Duedate: ")
                .append(getDueDate())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Reminder: ")
                .append(getReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
