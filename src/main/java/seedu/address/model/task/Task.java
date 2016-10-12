package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a Task in the Task Manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Deadline deadline;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Deadline deadline, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadline, priority);
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.tags = tags;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDeadline(), source.getPriority(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Deadline getDeadline() {
        return deadline;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public void setName(Name name){
    	this.name = name;
    }
    
   @Override
    public void setDeadline(Deadline deadline){
    	this.deadline = deadline;
    }
   
    @Override
    public void setPriority(Priority priority){
    	this.priority = priority;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
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
        return Objects.hash(name, deadline, priority);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
