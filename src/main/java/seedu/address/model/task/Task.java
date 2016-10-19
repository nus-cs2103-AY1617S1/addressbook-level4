package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents an event or a task (with or without deadline) in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private boolean isEvent;
    private Name name;
    private Date date;
    private boolean taskDone;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date date, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, date, tags);
        this.taskDone=false;
        this.name = name;
        this.date = date;
        if (date instanceof EventDate) {
            isEvent = true;
        } else {
            isEvent = false;
        }
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getTags());
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
    public boolean isEvent() {
        return isEvent;
    }
    
    @Override
    public boolean isDone(){
    	return taskDone;
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public void markAsDone() {
		taskDone=true;
		
	}

}
