package seedu.tasklist.testutil;

import java.sql.Date;
import java.util.Calendar;

import seedu.tasklist.model.tag.UniqueTagList;
import seedu.tasklist.model.task.*;

/**
 * A mutable Task object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

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
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setRecurringFrequency(String frequency) {
    	switch (frequency) {
	    case "daily": case "weekly": case "monthly": case "yearly": 
            this.isRecurring = true;
            this.recurringFrequency = frequency;
            break;
        default:
            this.isRecurring = false;
            this.recurringFrequency = "";
    	}
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
    	return this.recurringFrequency;
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
        if(!this.getStartTime().isMissing()){
        	sb.append("at " + this.getStartTime().toCardString() + " ");
        }
        if(!this.getEndTime().isMissing()){
        	sb.append("by " + this.getEndTime().toCardString() + " ");
        }
        sb.append("p/" + this.getPriority() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    //@@author A0146107M
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
	
	@Override
	public boolean isRecurring() {
		return this.isRecurring;
	}

	@Override
	public boolean isOverDue() {
		if(!isFloating()){
			if(!endTime.time.getTime().equals(new Date(0))){
				return endTime.time.getTimeInMillis() < System.currentTimeMillis();
			}
			else return false;
		}
		else 
			return false;
	}
	/*
	@Override
	public int compareTo(TestTask o){
		if(!this.startTime.equals(o.getStartTime())){
			return this.startTime.compareTo(o.getStartTime());
		}
		else {
			return this.priority.compareTo(o.getPriority());
		}
	}
	*/

    public boolean hasStartTime() {
        return new StartTime((long)0).equals(startTime);
    }
    
    public boolean hasEndTime() {
        return new EndTime((long)0).equals(endTime);
    }
		
	@Override
    public boolean isEvent() {
		return hasStartTime() && hasEndTime();
    }
    
	@Override
    public boolean isToday() {
    	if(!hasEndTime()) return false;
    	return endTime.time.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
    
	@Override
    public boolean isTomorrow() {
    	if(!hasEndTime()) return false;
    	return endTime.time.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1;
    }
	//@@author
	@Override
	public int compareTo(TestTask o) {
		// compare floating tasks
		if (this.startTime.equals(o.getStartTime()) && this.endTime.equals(o.getEndTime())) {
			return this.priority.compareTo(o.getPriority());
		}
		else {
			if (this.startTime.equals(o.getStartTime())) {
			    return this.endTime.compareTo(o.getEndTime());
			}
			else if (this.endTime.equals(o.getEndTime())) {
			    return this.startTime.compareTo(o.getStartTime());
			}
			// if only has end time
			else if(this.startTime.toCardString().equals("-")) {
			    return this.endTime.compareTo(o.getStartTime());
			}
			else if (o.getStartTime().toCardString().equals("-")){
			    return this.startTime.compareTo(o.getEndTime());
			}
			// if only has start time
			else {
			    return this.startTime.compareTo(o.getStartTime());
			}
		}
	}
}
