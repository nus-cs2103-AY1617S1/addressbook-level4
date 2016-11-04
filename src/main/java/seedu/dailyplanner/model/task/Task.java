package seedu.dailyplanner.model.task;

import java.util.Date;
import java.util.Objects;

import seedu.dailyplanner.commons.util.CollectionUtil;
import seedu.dailyplanner.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

	private String taskName;
	private Date start;
	private Date end;
	private boolean isComplete;
	private boolean isPinned;
	private UniqueTagList tags;

	/**
	 * Every field must be present and not null.
	 */
	public Task(String name, Date start, Date end, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, start, end, tags);
		this.taskName = name;
		this.start = start;
		this.end = end;
		this.tags = new UniqueTagList(tags); // protect internal tags from
												// changes in the arg list
		this.isComplete = false;
		this.isPinned = false;
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getName(), source.getStart(), source.getEnd(), source.getTags());
	}

	@Override
	public void setName(String name) {
		this.taskName = name;
	}

	@Override
	public void setStart(Date startDate) {
		this.start = startDate;
	}

	@Override
	public void setEnd(Date endDate) {
		this.end = endDate;
	}

	@Override
	public void markAsComplete() {
		this.isComplete = true;
	}

	@Override
	public void markAsNotComplete() {
		this.isComplete = false;
	}

	public void pin() {
		this.isPinned = true;
	}

	public void unpin() {
		this.isPinned = false;
	}

	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public Date getStart() {
		return start;
	}

	@Override
	public Date getEnd() {
		return end;
	}

	@Override
	public String getCompletion() {
		return (isComplete) ? "COMPLETED" : "NOT COMPLETED";
	}
	
	@Override
	public boolean isComplete() {
		return isComplete;
	}

	public boolean isPinned() {
		return isPinned;
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
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(taskName, start, end, isComplete, isPinned, tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public int compareTo(Task o) {
		if (this.getStart().before(o.getStart())) {
			return -1;
		} else if (this.getStart().after(o.getStart())) {
			return 1;
		} else {
			if (this.getEnd().before(o.getEnd())) {
				return -1;
			} else if (this.getEnd().after(o.getEnd())) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
