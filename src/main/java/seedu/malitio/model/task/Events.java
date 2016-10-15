package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.model.tag.UniqueTagList;

public class Events extends Schedule implements ReadOnlySchedule {
    private Name name;
    private DateTime start = null;
    private DateTime end = null;
    private UniqueTagList tags;

	
	public Events(Name name, DateTime start, DateTime end, UniqueTagList tags) {
		super(name,tags);		
		this.start = start;
		this.end = end;
	}
	
    /**
     * Copy constructor.
     */
    public Events(ReadOnlySchedule source) {
        this(source.getName(), source.getStart(), source.getEnd(), source.getTags());
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
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlySchedule// instanceof handles nulls
                && this.isSameStateAs((ReadOnlySchedule) other));
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
    public DateTime getDue() {
        return null;
    }

    @Override
    public DateTime getStart() {
        return start;
    }

    @Override
    public DateTime getEnd() {
        return end;
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getStart())
                .append(getEnd())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
