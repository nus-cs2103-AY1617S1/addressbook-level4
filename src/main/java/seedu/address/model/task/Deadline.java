package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Deadline extends DatedTask {

    private Name name;
    private Description description;
    private Duple end;
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     * 
     * @param name Name of task
     * 
     */
    public Deadline(Name name, Description description, Date endDate, Time endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, description, endDate, endTime, tags);
        this.name = name;
        this.description = description;
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
        else if (!(object instanceof Deadline)){
            return false;
        }
        else {
            Deadline other = (Deadline) object;
            return (other.getName().equals(this.getName()) // state checks here onwards
                 && other.getDescription().equals(this.getDescription()));
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, end, tags);
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
