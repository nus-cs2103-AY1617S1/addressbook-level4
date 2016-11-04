package seedu.unburden.testutil;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;

/**
 * A mutable person object. For testing only.
 * Author A0147986H
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private TaskDescription taskD;
    private Date date;
    private Time startTime;
    private Time endTime;
    private UniqueTagList tags;
    private boolean done;
    private boolean overdue;

    public TestTask() {
        tags = new UniqueTagList();
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskDescription getTaskDescription() {
        return taskD;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
    @Override
    public Time getStartTime() {
        return startTime;
    }
    
    @Override
    public Time getEndTime() {
        return endTime;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    @Override
	public boolean getDone() {
		return done;
	}
    
    @Override 
    public boolean getOverdue() {
    	return overdue;
    }
    
    

    public void setName(Name name) {
        this.name = name;
    }
    
    
    public void setTaskDescription(TaskDescription taskD) {
        this.taskD = taskD;
    }
      
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    
    public void setEndTime(Time endTime){
        this.endTime = endTime;
    }
    
    
    public void setTags(UniqueTagList taskD) {
        this.tags = taskD;
    }
    
    public void setDone(boolean done){
    	this.done = done;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().getFullName() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	
	@Override
	public String getDoneString() {
		if(done == true){
			return "Task Done!";
		}
		else{
			return "Task unDone!";
		}
	}
	
}
