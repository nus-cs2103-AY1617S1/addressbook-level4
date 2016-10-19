package seedu.ggist.testutil;

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
    private UniqueTagList tags;
    private boolean done;

    public TestTask() {
        tags = new UniqueTagList();
    }

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
    public UniqueTagList getTags() {
        return tags;
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
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
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
    }
}
