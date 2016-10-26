package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import seedu.address.model.task.TaskType;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;


public class Task implements ReadOnlyTask, Comparable<ReadOnlyTask> {

	private TaskType taskType;
	private Name name;
	private Status status;
	private Optional<LocalDateTime> startDate;
	private Optional<LocalDateTime> endDate;
	private UniqueTagList tags;
	
	
	/**
     * Constructor for events, deadlines and somedays.
     * 
     * @throws IllegalArgumentException if non-empty date optionals are passed in for the wrong task type.
     */
    public Task(Name name, TaskType taskType, Status status, Optional<LocalDateTime> startDate, 
    		Optional<LocalDateTime> endDate, UniqueTagList tags) throws IllegalArgumentException {
    	
    	assert !CollectionUtil.isAnyNull(name, taskType, status, startDate, endDate, tags);
    	
    	if (startDate.isPresent() && taskType.value != TaskType.Type.EVENT) {
    		throw new IllegalArgumentException("Only events can have start dates");
    	}
    	if (endDate.isPresent() && taskType.value == TaskType.Type.SOMEDAY) {
    		throw new IllegalArgumentException("Only events and deadlines can have end dates");
    	}
    	
    	this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTaskType(), source.getStatus(), source.getStartDate(), source.getEndDate(), source.getTags());
    }
    
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public TaskType getTaskType() {
        return taskType;
    }
    
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Optional<LocalDateTime> getEndDate() {
    	return endDate;
    }
    
    public void setEndDate(LocalDateTime date) {
    	if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
    		this.setTaskType(new TaskType("deadline"));
    	}
    	endDate = Optional.of(date);
    }
    
    public Optional<LocalDateTime> getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime date) throws UnsupportedOperationException {
        if (!endDate.isPresent()) {
            throw new UnsupportedOperationException("End date missing, start date cannot be set");
        }
        else {
            this.setTaskType(new TaskType("event"));
        	startDate = Optional.of(date);
        }
    }

    /**
     * The returned TagList is a deep copy of the internal TagList, changes on
     * the returned list will not affect the task's internal tags.
     */
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, taskType, status, startDate, endDate, tags);
    }

    @Override
    public String toString() {
        return getAsText();
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
