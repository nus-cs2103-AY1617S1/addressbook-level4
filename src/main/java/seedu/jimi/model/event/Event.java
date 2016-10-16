package seedu.jimi.model.event;

import java.util.Objects;

import seedu.jimi.commons.util.CollectionUtil;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;

public class Event implements ReadOnlyTask {

    private Name name;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;
    private boolean isCompleted;
    
    public Event(Name name, DateTime start, DateTime end, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, start, end, tags);
        assert start.compareTo(end) <= 0;
        this.isCompleted = false;
        this.name = name;
        this.start = start;
        this.end = end;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
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

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
               .append(getEnd())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }


}
