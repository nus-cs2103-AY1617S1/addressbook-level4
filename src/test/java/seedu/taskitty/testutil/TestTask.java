//@@author A0139930B
package seedu.taskitty.testutil;

import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

    private Name name;
    private TaskPeriod period;
    private boolean isDone;
    private boolean isOverdue;
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
    
    //@@author
    public String getEditCommand(int index, String category) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit " + category + index + " " + this.getName().fullName + " ");
        if (period.getStartDate() != null && period.getStartTime() != null) {
            sb.append(period.getStartDate().toString() + " " + period.getStartTime().toString() + " to ");
        }
        if (period.getEndDate() != null && period.getEndTime() != null) {
            sb.append(period.getEndDate().toString() + " " + period.getEndTime().toString() + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
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
	
	//@@author A0130853L
	public void markAsDone() {
		isDone = true;
	}
	
	@Override
	public boolean isOverdue() {
		return isOverdue;
	}
	
	//@@author
	@Override
    public int compareTo(TestTask taskToCompare) {
	    // sort all tasks that are done to the back of the list
        if (this.getIsDone() && !taskToCompare.getIsDone()) {
            return 1;
        } else if (!this.getIsDone() && taskToCompare.getIsDone()) {
            return -1;
        } else {        
            int periodCompare = this.period.compareTo(taskToCompare.getPeriod());
            //If no difference is found in period, compare using name
            if (periodCompare == 0) {
                return this.getName().fullName.compareTo(taskToCompare.getName().fullName);
            } else {
                return periodCompare;
            }
        }
        
	}
}
