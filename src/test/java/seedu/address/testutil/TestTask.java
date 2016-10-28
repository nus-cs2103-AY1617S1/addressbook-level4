package seedu.address.testutil;

import java.util.Calendar;
import java.util.Date;
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
 */

//@@author a0125284

public class TestTask extends TestActivity implements ReadOnlyTask{

    private DueDate duedate;
    private Priority priority;
    
    private static int DAYS_WARNING = -3;
	
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
