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
    
    public TestTask(TestTask task){
    	name = task.getName();
    	status = task.getStatus();
    	startDate = task.getStartDate();
    	endDate = task.getEndDate();
    	taskType = task.getTaskType();
    	tags = task.getTags();
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
    
    public void setTaskType(TaskType taskType){
    	this.taskType = taskType;
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
        sb.append(this.getName().value + "'");
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
        sb.append("edit " + index + " '");
        sb.append(this.getName().value + "'");
        if (this.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	sb.append(" by " + this.getEndDate().toString());
        } else if (this.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	sb.append(" from " + this.getStartDate().toString());
        	sb.append(" to " + this.getEndDate().toString());
        }
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    public TestTask convertoToPostEditTestTask(TaskType newTaskType, Name newName, Optional<LocalDateTime> newStartDate, Optional<LocalDateTime> newEndDate){
    	TestTask postEdit = new TestTask(this);
    	postEdit.setTaskType(newTaskType);
    	postEdit.setName(newName);
    	
    	if(newStartDate.isPresent()){
        	postEdit.setStartDate(newStartDate.get());
    	}
    	
    	if(newEndDate.isPresent()){
        	postEdit.setEndDate(newEndDate.get());	
    	}
    	
    	return postEdit;
    }
	@Override
	public int compareTo(ReadOnlyTask other) {
		int statusCompare = this.getStatus().compareTo(other.getStatus());
		if (statusCompare != 0) {
			return statusCompare;
		}
		else {
			LocalDateTime thisDate = this.getStartDate().orElse(this.getEndDate().orElse(LocalDateTime.MAX));
			LocalDateTime otherDate = other.getStartDate().orElse(other.getEndDate().orElse(LocalDateTime.MAX));
			
			int dateCompare = thisDate.compareTo(otherDate);
			
			if (dateCompare != 0) {
				return dateCompare;
			}
			else {
				return this.getName().compareTo(other.getName());
			}
		}
	}
    
}
