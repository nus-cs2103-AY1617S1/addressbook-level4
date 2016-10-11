package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a Task in the task man.
 * Guarantees: Title and UniqueTagList are present and not null, field values are validated.
 */
public class Task extends Event implements ReadOnlyTask, MutableTagsEvent {

    private Deadline deadline;
    private Status status;

    public Task(@Nonnull Title title, @Nonnull UniqueTagList tags,
                @Nullable Deadline deadline,
                @Nullable Schedule schedule, @Nullable Frequency frequency) {
        super(title, tags, schedule, frequency);
        this.deadline = deadline;
        this.status = new Status();
    }

    /**
     * Copy constructor
     */
    public Task(@Nonnull ReadOnlyTask source) {
        this(source.getTitle(), source.getTags(),
                source.getDeadline().orElse(null),
                source.getSchedule().orElse(null),
                source.getFrequency().orElse(null));
        setStatus(source.getStatus());
    }

    @Override
    public Title getTitle() {
        return super.getTitle();
    }

    @Override
    public Optional<Deadline> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    @Override
	public Optional<Schedule> getSchedule() {
        return super.getSchedule();
	}

	@Override
	public Optional<Frequency> getFrequency() {
        return super.getFrequency();
	}

	@Override
	public Status getStatus() {
		return status;
	}

    @Override
    public UniqueTagList getTags() {
        return super.getTags();
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        super.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Task // instanceof handles nulls
                && this.isSameStateAs((Task) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(
                super.getTitle(),
                deadline,
                super.getSchedule(),
                status,
                super.getFrequency(),
                super.getTags()
        );
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
