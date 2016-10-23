package seedu.ggist.testutil;

import java.util.Date;

import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;    
    private TaskTime endTime;
    private Priority priority;
    private Date start;
    private Date end;
    private boolean done;
    private boolean undo;

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
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
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
   
    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskName().taskName + ",");
        sb.append(this.getStartDate().value + ",");
        sb.append(this.getStartTime().value + ",");
        sb.append(this.getEndDate().value + ",");
        sb.append(this.getEndTime().value + ",");
        sb.append(this.getPriority().value);
        return sb.toString();
    }

    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public void setDone() {
        done = true;
    }

    @Override
    public void setUnDone() {
        done = false;
        undo = true;
    }

    @Override
    public Date getStartDateTime() {
        return start;
    }

    @Override
    public Date getEndDateTime() {
        return end;
    }

}
