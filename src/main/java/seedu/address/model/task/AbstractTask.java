package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

public abstract class AbstractTask implements TMReadOnlyTask {
	
	public abstract Name getName();
	public abstract void setName(Name name);
	
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
