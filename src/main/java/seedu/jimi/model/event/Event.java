package seedu.jimi.model.event;

import java.util.Objects;

import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * Represents a event with a start date and a end date
 * 
 * @@author A0148040R
 *
 */
public class Event implements ReadOnlyTask {

    private Name name;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;
    private boolean isCompleted;
    private Priority priority;
    
    public Event(Name name, DateTime start, DateTime end, UniqueTagList tags, boolean isCompleted, Priority priority) {
        this(name, start, end, tags, priority);
        this.isCompleted = isCompleted;
    }
    
    public Event(Name name, DateTime start, DateTime end, UniqueTagList tags, Priority priority) {
        assert !CollectionUtil.isAnyNull(name, start, end, tags);
        assert start.compareTo(end) <= 0;
        this.isCompleted = false;
        this.name = name;
        this.start = start;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.priority = priority;
    }
    
    /**
     * Copy constructor.
     */
    public Event(Event source) {
        this(source.getName(), ((Event) source).getStart(), ((Event) source).getEnd(), source.getTags(),
                source.isCompleted(), source.getPriority());
    }
    
    
    @Override
    public Name getName() {
        return name;
    }
    
    public DateTime getStart() {
        return start;
    }
    
    public DateTime getEnd() {
        return end;
    }

    @Override
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean c) {
        isCompleted = c;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public Priority getPriority()   {
        return priority;
    }

    @Override
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
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                    && (other).getName().equals(this.getName()) // state checks here onwards
                    && (other).isCompleted() == this.isCompleted()
                    && ((Event)other).getStart().equals(this.getStart())
                    && ((Event)other).getEnd().equals(this.getEnd())
                );
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, start, end, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" Start: ")
               .append(getStart())
               .append(" End: ")
               .append(getEnd() == null ? "none" : getEnd())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Priority: ")
               .append(getPriority());
        return builder.toString();
    }


}
