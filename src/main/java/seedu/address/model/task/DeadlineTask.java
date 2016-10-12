package seedu.address.model.task;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class DeadlineTask extends AbstractTask implements TMReadOnlyTask {
	
	private Name name;
	private Date byDate;
    private Status status;
    public final String taskType = "Deadline";
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public DeadlineTask(Name name, Date date, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, status, tags);
        this.name = name;
        this.byDate = date;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public DeadlineTask(Name name, Date date, Status status) {
        assert !CollectionUtil.isAnyNull(name, date, status);
        this.name = name;
        this.byDate = date;
        this.status = status;
    }

    /**
     * Copy constructor.
     * @throws IllegalArgumentException if source.getEndDate() returns an empty optional
     */
    public DeadlineTask(TMReadOnlyTask source) {
        this(source.getName(), source.getEndDate().orElseThrow(IllegalArgumentException::new), source.getStatus(), source.getTags());
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
    
    @Override
    public void setStartDate(Date date) throws UnsupportedOperationException {
    	throw new UnsupportedOperationException("Start date cannot be set on a deadline task");
    }
    
    
    @Override
    public Optional<Date> getEndDate() {
        return Optional.of(byDate);
    }
    
    @Override
    public void setEndDate(Date date) {
    	this.byDate = date;
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
        return Objects.hash(name, byDate, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
