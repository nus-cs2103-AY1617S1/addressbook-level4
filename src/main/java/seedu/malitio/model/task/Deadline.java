package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.model.tag.UniqueTagList;


public class Deadline implements ReadOnlyDeadline{
    
    private Name name;
    private DateTime due;
    private UniqueTagList tags;

    /**
     * Constructor for deadlines.
     */
	public Deadline(Name name, DateTime due, UniqueTagList tags) {
		this.name = name;
		this.due = due;
		this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
	}
	
    /**
     * Copy constructor.
     */
    public Deadline(ReadOnlyDeadline source) {
        this(source.getName(), source.getDue(), source.getTags());
    }


    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DateTime getDue() {
        return due;
    }
    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this deadline's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyDeadline// instanceof handles nulls
                && this.isSameStateAs((ReadOnlyDeadline) other));
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, due, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
