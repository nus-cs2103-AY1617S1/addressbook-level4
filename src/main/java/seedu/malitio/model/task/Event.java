package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.UniqueTagList;

public class Event implements ReadOnlyEvent {
    private Name name;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;
	
    private static final String MESSAGE_INVALID_EVENT = "Event must start before it ends!";

    /**
     * Constructor for events.
     */
    public Event(Name name, DateTime start, DateTime end, UniqueTagList tags) 
            throws IllegalValueException {
        
       if(!isValidEvent(start, end)) {       
           throw new IllegalValueException(MESSAGE_INVALID_EVENT);
       }
       this.name = name;
       this.start = start;
       this.end = end;
       this.tags = tags;
    }
	
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Event(ReadOnlyEvent source) throws IllegalValueException {
        this(source.getName(), source.getStart(), source.getEnd(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
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
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this event's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent// instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
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
    
    private static boolean isValidEvent(DateTime start, DateTime end) {
        if (end.compareTo(start) > 0) {
            return true;
        }
        return false;
    }

}
