package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;


public abstract class TMTask implements TMReadOnlyTask {

	public final TaskType taskType;
	private Name name;
	private Status status;
	private Optional<Date> startDate;
	private Optional<Date> endDate;
	private UniqueTagList tags;
	
	
	/**
	 * Construct a 'deadline' task 
	 * 
	 * @param tags may be empty.
	 * Every field must be present and not null.
	 */
	public TMTask(Name name, Status status, Date date, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, status, date, tags);
		this.taskType = TaskType.DEADLINE;
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
	public TMTask(Name name, Status status, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, status, tags);
		this.taskType = TaskType.SOMEDAY;
		this.name = name;
		this.status = status;
		this.startDate = Optional.empty();
		this.endDate = Optional.empty();
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
	
	
	public Optional<Date> getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date date) throws UnsupportedOperationException {
		if (taskType.equals(TaskType.DEADLINE)) {
			throw new UnsupportedOperationException("Start date cannot be set on a deadline task");
		}
		else if (taskType.equals(TaskType.SOMEDAY)) {
			throw new UnsupportedOperationException("Start date cannot be set on a someday task");
		}
		else {
			startDate = Optional.of(date);
		}
	}

	
	public Optional<Date> getEndDate() {
		return endDate;
	}

	public void setEndDate(Date date) throws UnsupportedOperationException {
		if (taskType.equals(TaskType.SOMEDAY)) {
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

}
