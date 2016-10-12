package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public abstract class TMTask implements TMReadOnlyTask {
	
	private Name name;
	private Optional<Date> fromDate;
	private Optional<Date> byDate;
    private Status status;
    public final String taskType;
    private UniqueTagList tags;
    
    /**
     * Creating Someday task
     * Every field must be present and not null.
     */
    public TMTask(Name name, Status status) {
    	assert !CollectionUtil.isAnyNull(name, status);
        this.taskType = "Someday";
    	this.name = name;
        this.status = status;
    }
    
    public TMTask(Name name, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, tags);
        this.taskType = "Someday";
        this.name = name;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Creating Deadline task
     * Every field must be present and not null.
     */
    public TMTask(Name name, Date date, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, status, tags);
        this.taskType = "Deadline";
        this.name = name;
        this.byDate = date;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public TMTask(Name name, Date date, Status status) {
        assert !CollectionUtil.isAnyNull(name, date, status);
        this.taskType = "Deadline";
        this.name = name;
        this.byDate = date;
        this.status = status;
    }

    
	public Name getName() {
		return name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
    public abstract Optional<Date> getStartDate();
    public abstract void setStartDate(Date date);
    
    public Date getEndDate() {
    	//Optional<>.get() throws NoSuchElementException if empty
    	return byDate.get();
    }
    public abstract void setEndDate(Date date);
    
    public abstract Status getStatus();
    public abstract void setStatus(Status status);
    
    public abstract String getTaskType();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    public abstract UniqueTagList getTags();
	
}
