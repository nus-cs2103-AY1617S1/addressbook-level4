package seedu.tasklist.testutil;

import java.sql.Date;

import seedu.tasklist.commons.util.RecurringUtil;
import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskDetails taskDetails;
    private int uniqueID;
    private EndTime endTime;
    private StartTime startTime;
    private Priority priority;
    private UniqueTagList tags;
    private boolean isComplete;
    private boolean isRecurring;
    private String recurringFrequency;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }
    
    public void setRecurringFrequency(String frequency) {
        this.recurringFrequency = frequency;
    }
    
    public void setRecurringTime() {
	    if (isRecurring && isComplete && !this.recurringFrequency.equals("")) {
	        RecurringUtil.updateRecurringDate(startTime.startTime, recurringFrequency);
	    	RecurringUtil.updateRecurringDate(endTime.endTime, recurringFrequency);
	        if (!startTime.isMissing() || !endTime.isMissing()) {
	            isComplete = false;
	        }
	    }
	}
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public TaskDetails getTaskDetails() {
        return taskDetails;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }
    
    @Override
	public String getRecurringFrequency() {
	    return recurringFrequency;
	}

    @Override
    public int getUniqueID() {
        return uniqueID;
    }
    
    public void markAsComplete() {
        isComplete = true;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskDetails() + " ");
        sb.append("at " + this.getStartTime().toString() + " ");
        sb.append("by " + this.getEndTime().toString() + " ");
        sb.append("p/" + this.getPriority() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

	@Override
	public boolean isFloating() {
		return endTime.isMissing()&&startTime.isMissing();
	}
	
	public boolean isRecurring() {
	    return this.isRecurring;
	}

	@Override
	public boolean isOverDue() {
		if(!isFloating()){
			if(!endTime.endTime.getTime().equals(new Date(0))){
				return endTime.endTime.getTimeInMillis() < System.currentTimeMillis();
			}
			else return false;
		}
		else 
			return false;
	}
}
