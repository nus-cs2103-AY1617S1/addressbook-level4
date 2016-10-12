package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;


public class Task implements ReadOnlyTask {

	public final TaskType taskType;
	private Name name;
	private Status status;
	private Optional<LocalDateTime> startDate;
	private Optional<LocalDateTime> endDate;
	private UniqueTagList tags;
	
	
	/**
	 * Construct an 'event' task 
	 * 
	 * @param tags may be empty.
	 * Every field must be present and not null.
	 */
    public Task(Name name, Status status, LocalDateTime startDate, LocalDateTime endDate, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, startDate, endDate, tags);
        this.taskType = new TaskType("event");
        this.name = name;
        this.status = status;
        this.startDate = Optional.of(startDate);
        this.endDate = Optional.of(endDate);
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
	
	/**
	 * Construct a 'deadline' task 
	 * 
	 * @param tags may be empty.
	 * Every field must be present and not null.
	 */
    public Task(Name name, Status status, LocalDateTime date, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, date, tags);
        this.taskType = new TaskType("deadline");
        this.name = name;
        this.status = status;
        this.startDate = Optional.empty();
        this.endDate = Optional.of(date);
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Construct a 'someday' task
     * 
     * @param tags may be empty.
     * Every field must be present and not null.
     */
    public Task(Name name, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, tags);
        this.taskType = new TaskType("someday");
        this.name = name;
        this.status = status;
        this.startDate = Optional.empty();
        this.endDate = Optional.empty();
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTaskType(), source.getStatus(), source.getStartDate(), source.getEndDate(), source.getTags());
    }
    
    /**
     * Copy constructor.
     */
    public Task(Name name, TaskType taskType, Status status, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate, UniqueTagList tags) {
    	assert !CollectionUtil.isAnyNull(name, status, tags);
        this.taskType = taskType;
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
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
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Optional<LocalDateTime> getStartDate() {
        return startDate;
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

    public Optional<LocalDateTime> getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime date) throws UnsupportedOperationException {
        if (taskType.value.equals(TaskType.Type.SOMEDAY)) {
            throw new UnsupportedOperationException("End date cannot be set on a someday task");
        }
        else {
            endDate = Optional.of(date);
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
}
