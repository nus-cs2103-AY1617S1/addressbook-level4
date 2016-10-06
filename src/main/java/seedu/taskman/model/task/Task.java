package seedu.taskman.model.task;

import seedu.taskman.commons.util.CollectionUtil;
import seedu.taskman.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task man.
 * Guarantees: Title and UniqueTagList are present and not null, field values are validated.
 */
public class Task implements EventInterface {

    private Title title;
    private Deadline deadline;
    private Status status;
    private Recurrence recurrence;
	private Schedule schedule;

    private UniqueTagList tags;

    /**
     * Only title and tags field must be present and not null.
     */
    public Task(Title title, Deadline deadline,
    		Status status, Recurrence recurrence, Schedule schedule, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, tags);
        this.title = title;
        this.deadline = deadline;
        this.status = status;
        this.recurrence = recurrence;
        this.schedule = schedule;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(EventInterface source) {
        this(source.getTitle(), null, null, source.getRecurrence(), source.getSchedule(), source.getTags());
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public Deadline getDeadline() {
        return deadline;
    }
    
	public Status getStatus() {
		return status;
	}

	@Override
	public boolean isRecurring() {
		return recurrence == null;
	}

	@Override
	public boolean isScheduled() {
		return schedule == null;
	}

	@Override
	public Recurrence getRecurrence() {
		return recurrence;
	}

	@Override
	public Schedule getSchedule() {
		return schedule;
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
                || (other instanceof EventInterface // instanceof handles nulls
                && this.isSameStateAs((EventInterface) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, deadline, status, recurrence, schedule, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
