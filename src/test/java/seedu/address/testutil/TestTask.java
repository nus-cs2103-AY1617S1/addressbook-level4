package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.person.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Detail detail;
    private DueByDate dueByDate;
    private DueByTime dueByTime;
    private Priority priority;
    private Done done;
    private UniqueTagList tags;

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
    public Done getDone() {
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
        sb.append(this.getDetail() + " ");
        sb.append(this.getDueByDate().value + " ");
        sb.append(this.getDueByTime().value + " ");
        sb.append(this.getPriority().value + " ");
        sb.append(this.getDone().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("-" + s.tagName + " "));
        return sb.toString();
    }
}
