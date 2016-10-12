package seedu.address.model.task;

import java.util.Date;
import java.util.Optional;

import seedu.address.model.tag.UniqueTagList;

public abstract class AbstractTask implements TMReadOnlyTask {
	
	public abstract Optional<Name> getName();
	public abstract void setName(Name name);
	
    public abstract Optional<Date> getDate();
    public abstract void setDate(Date date);
    
    public abstract Optional<Status> getStatus();
    public abstract void setStatus setStatus(Status status);
    
    public abstract String getTaskType();
    
    

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();
	
}
