package seedu.whatnow.testutil;

import java.util.Objects;

import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private String date;
    private String taskDate;
    private String startDate;
    private String endDate;
    private String taskTime;
    private String startTime;
    private String endTime;
    private UniqueTagList tags;
    private String status;
    private String taskType;//todo or schedule
    

    public TestTask() {
        setDate("");
        setTaskType("");
        tags = new UniqueTagList();
    }
    
    @Override
    public Name getName() {
        return this.name;
    }
    
    public void setName(Name name) {
        this.name = name;
    }    
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    @Override
    public String getStatus() {
        return this.status;
    }

    public String setStatus(String status) {
        return this.status = status;
    }
    
    @Override
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, status, tags, taskType, date);
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add \"" + this.getName().fullName + "\" ");
        if (!this.getDate().equals(null) && !this.getDate().equals(""))
            sb.append("on" + " " + this.getDate());
        this.getTags().getInternalList().stream().forEach(s -> sb.append(" t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public String getTaskDate() {
		return taskDate;
	}

    @Override
    public String getStartDate() {
        return startDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public String getTaskTime() {
        return taskTime;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }
}
