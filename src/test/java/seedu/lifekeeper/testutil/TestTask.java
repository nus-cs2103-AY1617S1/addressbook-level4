package seedu.lifekeeper.testutil;

import java.util.Calendar;
import java.util.Date;
import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.DateUtil;
import seedu.lifekeeper.model.activity.task.DueDate;
import seedu.lifekeeper.model.activity.task.Priority;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;
import seedu.lifekeeper.model.tag.UniqueTagList;

/**
 * A mutable Task object. For testing only.
 *
 */

//@@author A0125284H

public class TestTask extends TestActivity implements ReadOnlyTask{

    private DueDate duedate;
    private Priority priority;
    
    private static int DAYS_WARNING = -3;
	
    public TestTask() throws IllegalValueException {
    	super();
    	this.duedate = new DueDate("");
    	this.priority = new Priority("");
    }
    
	public TestTask(TestTask source) {
		this.name = source.getName();
		this.reminder = source.getReminder();
        this.tags = new UniqueTagList(source.getTags());
        this.isCompleted = source.getCompletionStatus();
		this.duedate = source.getDueDate();
		this.priority = source.getPriority();
		
	}
	
	public TestTask(TestActivity source) {
		this.name = source.getName();
		this.reminder = source.getReminder();
        this.tags = new UniqueTagList(source.getTags());
        this.isCompleted = source.getCompletionStatus();
		setDueDate("");
		setPriority("");
		
	}

	@Override
	public DueDate getDueDate() {
        return duedate;
	}
	
    public void setDueDate(DueDate duedate) {
        this.duedate = duedate;
    }
    
    public void setDueDate(String duedate) {
        try {
            this.duedate = new DueDate(duedate);
        } catch (IllegalValueException e) {
            assert false;
            e.printStackTrace();
        }
        
    }

	@Override
	public Priority getPriority() {
		return priority;
	}
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setPriority(String priority) {
        try {
            this.priority = new Priority(priority);
        } catch (IllegalValueException e) {
            assert false;
            e.printStackTrace();
        } 
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
