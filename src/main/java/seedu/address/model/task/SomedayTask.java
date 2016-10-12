package seedu.address.model.task;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class SomedayTask extends AbstractTask implements TMReadOnlyTask {
	
	private Name name;
    private Status status;
    public final String taskType = "Someday";
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public SomedayTask(Name name, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, status, tags);
        this.name = name;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public SomedayTask(Name name, Status status) {
    	assert !CollectionUtil.isAnyNull(name, status);
        this.name = name;
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public SomedayTask(TMReadOnlyTask source) {
        this(source.getName(), source.getStatus(), source.getTags());
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    @Override
	public void setName(Name name) {
		this.name = name;
	}
    
    
    @Override 
    public Optional<Date> getStartDate() {
    	return Optional.empty();
    }
    
    /**
     * @throws UnsupportedOperationException when called. Someday tasks do not support setting start date.
     */
    @Override
    public void setStartDate(Date date) throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Start date cannot be set on a someday task");
    }
    
    @Override 
    public Optional<Date> getEndDate() {
    	return Optional.empty();
    }
    
    
    /**
     * @throws UnsupportedOperationException when called. Someday tasks do not support setting end date.
     */
    @Override
    public void setEndDate(Date date) throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("End date cannot be set on a someday task");
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    @Override
	public void setStatus(Status status) {
		this.status = status;
	}
    
    
	@Override
	public String getTaskType() {
		return taskType;
	}
	

    @Override
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
                || (other instanceof TMReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((TMReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
