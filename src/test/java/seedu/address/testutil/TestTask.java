package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Detail;
import seedu.address.model.task.Done;
import seedu.address.model.task.DueByDate;
import seedu.address.model.task.DueByTime;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;

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
        sb.append("add " + this.getDetail().details + " ");
        sb.append(this.getDueByDate().toString() + " ");
        sb.append(this.getDueByTime().toString() + " ");
        sb.append("/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("-" + s.tagName + " "));
        return sb.toString();
    }
}
