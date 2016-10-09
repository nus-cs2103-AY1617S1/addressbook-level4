package seedu.taskman.model.event;

import seedu.taskman.commons.util.CollectionUtil;
import seedu.taskman.model.tag.UniqueTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the task man.
 * Guarantees: Title and UniqueTagList are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Title title;
    private Frequency frequency;
	private Schedule schedule;

    private UniqueTagList tags;

    public Event(@Nonnull Title title, @Nonnull UniqueTagList tags,
    		@Nullable Schedule schedule, @Nullable Frequency frequency) {
        assert !CollectionUtil.isAnyNull(title, tags);
        this.title = title;
        this.frequency = frequency;
        this.schedule = schedule;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTitle(), source.getTags(),
        		source.getSchedule().orElse(null),
        		source.getFrequency().orElse(null));
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
	public Optional<Schedule> getSchedule() {
		return Optional.ofNullable(schedule);
	}

	public Optional<Frequency> getFrequency() {
		return Optional.ofNullable(frequency);
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
                || (other instanceof Event // instanceof handles nulls
                && this.isSameStateAs((Event) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, frequency, schedule, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
