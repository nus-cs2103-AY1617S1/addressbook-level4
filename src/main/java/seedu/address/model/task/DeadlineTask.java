package seedu.address.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class DeadlineTask implements TMReadOnlyTask{
	
	private Name name;
	private Date date;
    private Status status;
    public final String taskType = "Deadline";
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public DeadlineTask(Name name, Date date, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, status, tags);
        this.name = name;
        this.date = date;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public DeadlineTask(Name name, Date date, Status status) {
        assert !CollectionUtil.isAnyNull(name, date, status);
        this.name = name;
        this.date = date;
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public DeadlineTask(TMReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStatus(), source.getTags());
    }
    
    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public Date getDate() {
        return date;
    }
    
	@Override
	public String getTaskType() {
		return taskType;
	}
    
    @Override
    public Status getStatus() {
        return status;
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
        return Objects.hash(name, date, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
