package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public abstract class TMTask implements TMReadOnlyTask {
	
	private Name name;
	private Date byDate;
    private Status status;
    public final String taskType = "Someday";
    private UniqueTagList tags;
    
    /**
     * Creating Someday task
     * Every field must be present and not null.
     */
    public TMTask(Name name, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, tags);
        this.name = name;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public TMTask(Name name, Status status) {
    	assert !CollectionUtil.isAnyNull(name, status);
        this.name = name;
        this.status = status;
    }
    
    /**
     * Creating Deadline task
     * Every field must be present and not null.
     */
    public TMTask(Name name, Date date, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, status, tags);
        this.name = name;
        this.byDate = date;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public TMTask(Name name, Date date, Status status) {
        assert !CollectionUtil.isAnyNull(name, date, status);
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
    
    public abstract Optional<Date> getEndDate();
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
