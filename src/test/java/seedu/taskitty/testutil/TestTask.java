package seedu.taskitty.testutil;

import seedu.taskitty.model.tag.UniqueTagList;
import seedu.taskitty.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask, Comparable<TestTask> {

    private Name name;
    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;
    private TaskTime endTime;
    private int numArgs;
    private boolean isDone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(TaskDate startDate) {
        this.startDate = startDate;
    }
    
    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndDate(TaskDate endDate) {
        this.endDate = endDate;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }
    
    public void setNumArgs(int numArgs) {
        this.numArgs = numArgs;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskDate getStartDate() {
        return startDate;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }
    
    @Override
    public int getNumArgs() {
        return numArgs;
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
        if (startDate != null && startTime != null) {
            sb.append(startDate.toString() + " " + startTime.toString() + " to ");
        }
        if (endDate != null && endTime != null) {
            sb.append(endDate.toString() + " " + endTime.toString() + " ");
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
		return numArgs == 1;
	}
	
	@Override
	public boolean isDeadline() {
		return numArgs == 3;
	}
	
	@Override
	public boolean isEvent() {
		return numArgs == 5;
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
                    return this.getStartTime().time.compareTo(taskToCompare.getStartTime().time);                    
                }
            }
         // if event has same start date and start time, sort it by its end date or end time like deadline
            if (this.isEvent() || this.isDeadline()) {
                if (!this.getEndDate().equals(taskToCompare.getEndDate())) {
                    return this.getEndDate().getDate().compareTo(taskToCompare.getEndDate().getDate());
                } else if (!this.getEndTime().equals(taskToCompare.getEndTime())) {
                    return this.getEndTime().time.compareTo(taskToCompare.getEndTime().time);                    
                } 
            }
         // if event and deadline has all the same dates and times, sort by the name of the task like todo
            return this.getAsText().compareTo(taskToCompare.getAsText());
        } else {
            return this.getNumArgs() - taskToCompare.getNumArgs();
        } 
    }
}
