package seedu.jimi.model.task;

import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private boolean isCompleted;
    private Priority priority;

    public FloatingTask(Name name, UniqueTagList tags, boolean isCompleted, Priority priority) {
        this(name, tags, priority);
        this.isCompleted = isCompleted;
    }
    
    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Name name, UniqueTagList tags, Priority priority) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.isCompleted = false;
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.priority = priority;
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(ReadOnlyTask source) {
        this(source.getName(), source.getTags(), source.isCompleted(), source.getPriority());
    }
    

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }

    /**
     * Replaces this floating task's name with name provided.
     * @param name Name to be replaced by.
     */
    public void setName(Name name){
        this.name = name;
    }
    
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    /**
     * Set the task to be completed/incomplete.
     * @param isCompleted is true if task is completed.
     */
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    public void setPriority(Priority priority)  {
        this.priority = priority;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
    
    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other instanceof FloatingTask // instanceof handles nulls
                && (other).getName().equals(this.getName()) // state checks here onwards
                && (other).isCompleted() == this.isCompleted()
                && (other).getPriority().equals(this.getPriority())
                );
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Priority: ")
               .append(getPriority());
        return builder.toString();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

}