package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
    private Time time;
    private Venue venue;
    private Priority priority;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Time time, Venue venue, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.description = description;
        this.time = time;
        this.venue = venue;
        this.priority = priority;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getTime(), source.getVenue(), source.getPriority(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Venue getVenue() {
        return venue;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, time, venue, priority, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
