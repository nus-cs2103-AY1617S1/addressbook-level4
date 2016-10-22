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
    
    public String getEditCommand(int index, String category) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit " + category + " " + index + " " + this.getName().fullName + " ");
        if (startDate != null && startTime != null) {
            sb.append(startDate.toString() + " " + startTime.toString() + " to ");
        }
        if (endDate != null && endTime != null) {
            sb.append(endDate.toString() + " " + endTime.toString() + " ");
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
	
	@Override
    public int compareTo(TestTask taskToCompare) {
	    // sort all tasks that are done to the back of the list
        if (this.getIsDone() && !taskToCompare.getIsDone()) {
            return 1;
        } else if (!this.getIsDone() && taskToCompare.getIsDone()) {
            return -1;
        }
        if (this.getNumArgs() == taskToCompare.getNumArgs()) {
         // sort events according to their start time and end time
            if (this.isEvent()) {
                if (!this.getStartDate().equals(taskToCompare.getStartDate())) {
                    return this.getStartDate().getDate().compareTo(taskToCompare.getStartDate().getDate());
                } else if (!this.getStartTime().equals(taskToCompare.getStartTime())) {
                    return this.getStartTime().getTime().compareTo(taskToCompare.getStartTime().getTime());                    
                }
            }
         // if event has same start date and start time, sort it by its end date or end time like deadline
            if (this.isEvent() || this.isDeadline()) {
                if (!this.getEndDate().equals(taskToCompare.getEndDate())) {
                    return this.getEndDate().getDate().compareTo(taskToCompare.getEndDate().getDate());
                } else if (!this.getEndTime().equals(taskToCompare.getEndTime())) {
                    return this.getEndTime().getTime().compareTo(taskToCompare.getEndTime().getTime());                    
                } 
            }
         // if event and deadline has all the same dates and times, sort by the name of the task like todo
            return this.getAsText().compareTo(taskToCompare.getAsText());
        } else {
            return this.getNumArgs() - taskToCompare.getNumArgs();
        } 
    }
}
