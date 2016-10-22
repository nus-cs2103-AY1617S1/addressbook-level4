package seedu.taskitty.testutil;

import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private TaskPeriod period;
    private boolean isDone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setPeriod(TaskPeriod period) {
        this.period = period;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskPeriod getPeriod() {
        return period;
    }
    
    @Override
    public boolean getIsDone() {
        return isDone;
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
        sb.append("add " + this.getName().fullName + " ");
        if (period.getStartDate() != null && period.getStartTime() != null) {
            sb.append(period.getStartDate().toString() + " " + period.getStartTime().toString() + " to ");
        }
        if (period.getEndDate() != null && period.getEndTime() != null) {
            sb.append(period.getEndDate().toString() + " " + period.getEndTime().toString() + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getEditCommand(int index) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit " + index + " " + this.getName().fullName);        
        return sb.toString();
    }

    @Override
	public boolean isTodo() {
		return period.isTodo();
	}
	
	@Override
	public boolean isDeadline() {
		return period.isDeadline();
	}
	
	@Override
	public boolean isEvent() {
		return period.isEvent();
	}
}
