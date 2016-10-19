package tars.model.task;

import tars.commons.util.CollectionUtil;
import tars.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in tars. 
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DateTime dateTime;
    private Status status;
    private Priority priority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */

    public Task(Name name, DateTime dateTime, Priority priority, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, dateTime, priority, status, tags);
        this.name = name;
        this.dateTime = dateTime;
        this.priority = priority;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDateTime(), source.getPriority(), source.getStatus(), source.getTags());
    }
    
    /**
     * Default constructor.
     */
    public Task() {
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setStatus(Status status) {
        this.status = status;
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
        return Objects.hash(name, dateTime, priority, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
