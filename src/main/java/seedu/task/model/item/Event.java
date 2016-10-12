package seedu.task.model.item;

import seedu.task.commons.util.CollectionUtil;
import seedu.task.model.item.Description;
import seedu.task.model.item.Name;

import java.util.Objects;

/**
 * Represents a event in the task book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Name name;
    private Description description;
    private EventDuration eventDuration;

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Description description) {
    	this(name, description, null);    
    }
    
    public Event(Name name, Description description, EventDuration eventDuration) {
        assert !CollectionUtil.isAnyNull(name, description);
        this.name = name;
        this.description = description;
        this.eventDuration = eventDuration;
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getEvent(), source.getDescription(), source.getDuration());
    }

    @Override
    public Name getEvent() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }
    
    @Override
    public EventDuration getDuration() {
        return eventDuration;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyEvent // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
