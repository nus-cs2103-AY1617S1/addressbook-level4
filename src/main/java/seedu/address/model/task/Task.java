package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the Task Manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Priority priority;
    private Startline startline;
    private UniqueDeadlineList deadlines;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Startline startline, UniqueDeadlineList deadlines, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, deadlines, priority, tags);
        this.name = name;
        this.startline = startline;
        this.deadlines = deadlines;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartline(), source.getDeadlines(), source.getPriority(), source.getTags());
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
    public Startline getStartline(){
    	return startline;
    }

    @Override
    public UniqueDeadlineList getDeadlines() {
        return deadlines;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    /**
     * Replaces this person's deadlines with the deadlines in the argument deadline list.
     */
    public void setDeadlines(UniqueDeadlineList replacement) {
        deadlines.setDeadlines(replacement);
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
        return Objects.hash(name, deadlines, priority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }


}
