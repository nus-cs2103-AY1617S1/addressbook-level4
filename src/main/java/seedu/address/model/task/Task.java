package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import seedu.address.model.task.TaskType;
import seedu.address.model.task.TaskType.Type;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;


public class Task implements ReadOnlyTask, Comparable<ReadOnlyTask> {

	private TaskType taskType;
	private Name name;
	private Status status;
	private Optional<LocalDateTime> startDate;
	private Optional<LocalDateTime> endDate;
	private UniqueTagList tags;	
	
	//@@author A0143756Y
	public static final String MESSAGE_END_DATE_TIME_NOT_AFTER_START_DATE_TIME = "End date/ time cannot be after or equal to start date/ time.\n"
    				+ "Please re-enter command with valid start and end dates/ times.\n";
	
	public static final String MESSAGE_START_DATE_TIME_CANNOT_BE_SET_WITH_END_DATE_TIME_REMOVED = 
			"Start date/ time cannot be set with end date/ time removed.\n";
	
	public static final String MESSAGE_START_DATE_TIME_CANNOT_BE_SET_WITH_END_DATE_TIME_MISSING = 
			"End date/ time is missing, start date/ time cannot be set.\n";
	//@@author
	
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
    	
    	if(startDate.isPresent() && endDate.isPresent()){
    		LocalDateTime startDateTime = startDate.get();
    		LocalDateTime endDateTime = endDate.get();
    		
    		validateEndDateTimeAfterStartDateTime(startDateTime, endDateTime);
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
        if(this.status.equals(new Status("pending")) && 
        		getEndDate().orElse(LocalDateTime.MAX).isBefore(LocalDateTime.now())) {
        	this.status = new Status("overdue");
        }
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
            throw new IllegalArgumentException(MESSAGE_START_DATE_TIME_CANNOT_BE_SET_WITH_END_DATE_TIME_MISSING);
        }
        	
        this.setTaskType(new TaskType("event"));
        startDate = Optional.of(date);

    }
    
    //@@author A0139339W
    public void removeStartDate() {
    	this.startDate = Optional.empty();
    	this.taskType = new TaskType(Type.DEADLINE);
    }
    
    public void removeEndDate() {
    	if(startDate.isPresent()) {
    		this.endDate = this.startDate;
    		this.startDate = Optional.empty();
    		this.taskType = new TaskType(Type.DEADLINE);
    	} else {
    		this.endDate = Optional.empty();
        	this.taskType = new TaskType(Type.SOMEDAY);
    	}
    	
    }
    
    //@@author A0143756Y
    public static void validateEndDateTimeAfterStartDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime){
    	if(!endDateTime.isAfter(startDateTime)){
    		throw new IllegalArgumentException(MESSAGE_END_DATE_TIME_NOT_AFTER_START_DATE_TIME);
    	}
    }
    //@@author

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
