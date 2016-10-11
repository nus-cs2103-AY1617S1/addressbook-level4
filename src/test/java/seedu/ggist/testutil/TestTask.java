package seedu.ggist.testutil;

import seedu.ggist.model.tag.UniqueTagList;
import seedu.ggist.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private TaskName taskName;
    private TaskDate date;
    private TaskTime startTime;
    private TaskTime endTime;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public void setTaskDate(TaskDate date) {
        this.date = date;
    }

    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public TaskDate getDate() {
        return date;
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
        sb.append(this.getDate().value + ",");
        sb.append(this.getStartTime().value + ",");
        sb.append(this.getEndTime().value + ",");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
