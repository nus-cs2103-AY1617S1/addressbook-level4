package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task man.
 * Guarantees: Title and UniqueTagList are present and not null, field values are validated.
 */
public class Task extends Event implements ReadOnlyTask {

    // todo: fields to be optional, & created through static newInstance method instead ??
    private Deadline deadline;
    private Status status;

    /**
     * Only title and tags field must be present and not null.
     */
    public Task(Title title, Deadline deadline,
                Frequency frequency, Schedule schedule, UniqueTagList tags) {
        super(title, frequency, schedule, tags);
        this.deadline = deadline;
        this.status = new Status("");
    }

    public Task(Title title, Deadline deadline, Status status,
                Frequency frequency, Schedule schedule, UniqueTagList tags) {
        super(title, frequency, schedule, tags);
        this.deadline = deadline;
        this.status = status;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTitle(), source.getDeadline(),
                source.getFrequency(), source.getSchedule(), source.getTags());
    }

    @Override
    public Title getTitle() {
        return super.getTitle();
    }

    public Deadline getDeadline() {
        return deadline;
    }
    
	public Status getStatus() {
		return status;
	}

	@Override
	public boolean isRecurring() {
        return super.isRecurring();
	}

	@Override
	public boolean isScheduled() {
        return super.isScheduled();
	}

	public Frequency getFrequency() {
        return super.getFrequency();
	}

	@Override
	public Schedule getSchedule() {
        return super.getSchedule();
	}
    
    @Override
    public UniqueTagList getTags() {
        return super.getTags();
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
                status,
                super.getFrequency(),
                super.getSchedule(),
                super.getTags()
        );
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
