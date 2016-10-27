package seedu.cmdo.testutil;

import seedu.cmdo.model.tag.UniqueTagList;
import seedu.cmdo.model.task.Detail;
import seedu.cmdo.model.task.Done;
import seedu.cmdo.model.task.DueByDate;
import seedu.cmdo.model.task.DueByTime;
import seedu.cmdo.model.task.Priority;
import seedu.cmdo.model.task.ReadOnlyTask;
import seedu.cmdo.model.task.Task;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Detail detail;
    private DueByDate dueByDate;
    private DueByTime dueByTime;
    private Priority priority;
    private Done done = new Done();
    private UniqueTagList tags;
    private boolean block;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDetail(Detail detail) {
    	this.detail = detail;
    }
    
    public void setDueByDate(DueByDate dueByDate) {
    	this.dueByDate = dueByDate;
    }
    
    public void setDueByTime(DueByTime dueByTime) {
    	this.dueByTime = dueByTime;
    }
    
    public void setPriority(Priority priority) {
    	this.priority = priority;
    	
    }
    
    public void setDone(Done done) {
    	this.done = done;
    }

    @Override
    public Detail getDetail() {
        return detail;
    }

    @Override
    public DueByDate getDueByDate() {
        return dueByDate;
    }

    @Override
    public DueByTime getDueByTime() {
        return dueByTime;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public boolean getBlock() {
    	return block;
    }
    
    @Override
    public Done checkDone() {
    	return done;
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
        sb.append("add '" + this.getDetail().details + "' ");
        sb.append(this.getDueByDate().toString() + " ");
        sb.append(this.getDueByTime().toString() + " ");
        if (this.getPriority().value != "") 
        	sb.append("/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("-" + s.tagName + " "));
        return sb.toString();
    }
    
    //@@author A0139661Y
    public String getAddRangeCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add '" + this.getDetail().details + "' from ");
        sb.append(this.getDueByDate().start.toString() + " " + this.getDueByTime().start.toString() + " to ");
        sb.append(this.getDueByDate().end.toString() + " " + this.getDueByTime().end.toString() + " ");
        if (this.getPriority().value != "") 
        	sb.append("/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("-" + s.tagName + " "));
        return sb.toString();
    }
    //@@author A0141128R
    public String getBlockCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("block '" + this.getDetail().details + "' from ");
        sb.append(this.getDueByDate().start.toString() + " " + this.getDueByTime().start.toString() + " to ");
        sb.append(this.getDueByDate().end.toString() + " " + this.getDueByTime().end.toString() + " ");
        sb.append("/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("-" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public boolean isRange() {
		return dueByDate.isRange() || dueByTime.isRange();
	}
}
