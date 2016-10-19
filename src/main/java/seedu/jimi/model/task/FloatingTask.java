package seedu.jimi.model.task;

import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.testutil.TestFloatingTask;

import java.util.Objects;

/**
 * Represents a task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class FloatingTask implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    private boolean isCompleted;

    public FloatingTask(Name name, UniqueTagList tags, boolean isCompleted) {
        this(name, tags);
        this.isCompleted = isCompleted;
    }
    
    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.isCompleted = false;
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(FloatingTask source) {
        this(source.getName(), source.getTags(), source.isCompleted());
    }
    
    
    /* Constructor for testing purposes */
    public FloatingTask(TestFloatingTask source) {
        this(source.getName(), source.getTags(), source.isCompleted());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
        return builder.toString();
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }

}