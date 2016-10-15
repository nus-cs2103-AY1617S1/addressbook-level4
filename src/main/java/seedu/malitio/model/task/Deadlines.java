package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.model.tag.UniqueTagList;

public class Deadlines extends Schedule implements ReadOnlySchedule{

    private DateTime due;

	
	public Deadlines(Name name, DateTime due, UniqueTagList tags) {
		super(name,tags);
		this.due = due;
	}
	
    /**
     * Copy constructor.
     */
    public Deadlines(ReadOnlySchedule source) {
        this(source.getName(), source.getDue(), source.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlySchedule// instanceof handles nulls
                && this.isSameStateAs((ReadOnlySchedule) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public DateTime getDue() {
        return due;
    }

    @Override
    public DateTime getStart() {
        return null;
    }

    @Override
    public DateTime getEnd() {
        return null;
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getDue())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
