package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	public TaskType taskType;
	private Name name;
	private Status status;
	private Optional<LocalDateTime> startDate;
	private Optional<LocalDateTime> endDate;
	private UniqueTagList tags;
	
    public TestTask() {
        taskType = new TaskType("someday");
    	tags = new UniqueTagList();
    	startDate = Optional.empty();
    	endDate = Optional.empty();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void setTaskType(String taskType) {
        this.taskType = new TaskType(taskType);
    }
    
	public void setStartDate(String startDate) {
		this.startDate = Optional.of(LocalDateTime.parse(startDate));
	}
    
    public void setStartDate(LocalDateTime date) throws UnsupportedOperationException {
        if (taskType.value.equals(TaskType.Type.DEADLINE)) {
            throw new UnsupportedOperationException("Start date cannot be set on a deadline task");
        }
        else if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
            throw new UnsupportedOperationException("Start date cannot be set on a someday task");
        }
        else {
            startDate = Optional.of(date);
        }
    }
    
    public void setEndDate(LocalDateTime date) throws UnsupportedOperationException {
        if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
            throw new UnsupportedOperationException("End date cannot be set on a someday task");
        }
        else {
            endDate = Optional.of(date);
        }
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    @Override
    public Optional<LocalDateTime> getStartDate() {
        return startDate;
    }
    
    @Override
    public Optional<LocalDateTime> getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTaskType().value + " '");
        sb.append(this.getName().fullName + "'");
        if (this.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	sb.append(" by " + this.getEndDate().toString());
        } else if (this.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	sb.append(" from " + this.getStartDate().toString());
        	sb.append(" to " + this.getEndDate().toString());
        }
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getEditCommand(int index) {
    	StringBuilder sb = new StringBuilder();
        sb.append("edit " + this.getTaskType().value + " " + index + " '");
        sb.append(this.getName().fullName + "'");
        if (this.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	sb.append(" by " + this.getEndDate().toString());
        } else if (this.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	sb.append(" from " + this.getStartDate().toString());
        	sb.append(" to " + this.getEndDate().toString());
        }
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
