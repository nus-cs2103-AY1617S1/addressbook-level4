package seedu.address.model.person;

import java.util.Comparator;
import java.util.Objects;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Status.State;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a DatedTask in the to-do-list. Guarantees: details are present and
 * not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {

	private Name name;
	private Description description;
	private Datetime datetime;
	private Status status;

	private UniqueTagList tags;

	/**
	 * Every field must be present and not null.
	 */
	public Task(Name name, Description phone, Datetime datetime, Status status, UniqueTagList tags) {
		assert !CollectionUtil.isAnyNull(name, phone, datetime, status, tags);
		this.name = name;
		this.description = phone;
		this.datetime = datetime;
		this.status = status;
		this.tags = new UniqueTagList(tags); // protect internal tags from
												// changes in the arg list
	}

	/**
	 * Copy constructor.
	 */
	public Task(ReadOnlyTask source) {
		this(source.getName(), source.getDescription(), source.getDatetime(), source.getStatus(), source.getTags());
	}

	@Override
	public Name getName() {
		return name;
	}

	@Override
	public Description getDescription() {
		return description;
	}

	@Override
	public Datetime getDatetime() {
		return datetime;
	}

	@Override
	public Status getStatus() {
		return status;
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
				|| (other instanceof ReadOnlyTask // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyTask) other));
	}

	public void setName(Name name) {
		this.name = name;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public void setDatetime(Datetime datetime) {
		this.datetime = datetime;
	}

	public boolean setAsDone() {
		this.setStatus(new Status(State.DONE));
		return true;
	}

	public boolean setAsOverdue() {
		this.setStatus(new Status(State.OVERDUE));
		return true;
	}

	public boolean setAsNorm() {
		this.setStatus(new Status(State.NONE));
		return true;
	}

	private void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing
		// your own
		return Objects.hash(name, description, datetime, status, tags);
	}

	@Override
	public String toString() {
		return getAsText();
	}

	@Override
	public int compareTo(Task other) {
		return Comparators.NAME.compare(this, other);
	}

	public static class Comparators {

		public static Comparator<Task> NAME = new Comparator<Task>() {
			@Override
			public int compare(Task t1, Task t2) {
				return t1.getName().toString().compareTo(t2.getName().toString());
			}
		};
		public static Comparator<Task> DATE = new Comparator<Task>() {
			@Override
			public int compare(Task t1, Task t2) {
				Datetime d1 = t1.getDatetime();
				Datetime d2 = t2.getDatetime();

				java.util.Date dd1 = d1.getStartDate();
				java.util.Date dd2 = d2.getStartDate();

				if (dd1 == null) {
					return 0;
				} else if (dd2 == null) {
					System.out.println("hi");
					return 0;
				}

				return dd1.compareTo(dd2);
			}
		};
	}
}
