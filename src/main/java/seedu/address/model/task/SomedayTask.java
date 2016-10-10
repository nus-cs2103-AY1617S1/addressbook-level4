package seedu.address.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

public class SomedayTask implements TMReadOnlyTask {
	
	private Name name;
    private Status status;
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
    public Date getDate() {
    	return new Date(0);
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
        return Objects.hash(name, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
