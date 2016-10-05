package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Floating task in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Floating extends Task {

    private Name name;
    private Description description;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Floating(Name name, Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, description, tags);
        this.name = name;
        this.description = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Floating(ReadOnlyTask source) {
        this(source.getName(), source.getDescription(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
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
        else if (!(object instanceof Floating)){
            return false;
        }
        else {
            Floating other = (Floating) object;
            return (other.getName().equals(this.getName()) // state checks here onwards
                 && other.getDescription().equals(this.getDescription()));
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getAsText() {
        return null;
    }
    
    /*
    @Override
    private boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDescription().equals(this.getDescription())
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime()));
    }
    */
}
