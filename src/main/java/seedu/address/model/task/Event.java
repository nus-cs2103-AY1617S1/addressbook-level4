package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event extends Task {

    private Name name;
    private Description description;
    private Duple start, end;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * 
     * @param name Name of task
     * 
     */
    public Event(Name name, Description description, Date startDate, 
            Time startTime, Date endDate, Time endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, description, startDate, startTime, endDate, endTime, tags);
        this.name = name;
        this.description = description;
        this.start = new Duple(startDate, startTime);
        this.end = new Duple(endDate, endTime);
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    public Duple getStart() {
        return start;
    }

    public Duple getEnd() {
        return end;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) { // short circuit if same object
            return true;
        }
        else if (!(object instanceof Event)){
            return false;
        }
        else {
            Event other = (Event) object;
            return (other.getName().equals(this.getName()) // state checks here onwards
                 && other.getDescription().equals(this.getDescription()));
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, start, end, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getAsText() {
        // TODO Auto-generated method stub
        return null;
    }

}
