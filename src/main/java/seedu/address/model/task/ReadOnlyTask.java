package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a DatedTask in the to-do-list.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface ReadOnlyTask {

	Name getName();

	Description getDescription();

	Datetime getDatetime();

	Status getStatus();

	/**
	 * The returned TagList is a deep copy of the internal TagList, changes on
	 * the returned list will not affect the person's internal tags.
	 */
	UniqueTagList getTags();

	/**
	 * Returns true if both have the same state. (interfaces cannot override
	 * .equals)
	 */
	default boolean isSameStateAs(ReadOnlyTask other) {
		return other == this // short circuit if same object
				|| (other != null // this is first to avoid NPE below
						&& other.getName().equals(this.getName()) // state
						&& other.getDescription().equals(this.getDescription())
						&& other.getDatetime().equals(this.getDatetime()));
	}

	/**
	 * Formats the person as text, showing all contact details.
	 */
	default String getAsText() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getName());
		if (!getDescription().toString().isEmpty()) {
			builder.append(" Description: ").append(getDescription());
		}
		if (!getDatetime().getDateString().isEmpty()) {
			builder.append(" Date: ").append(getDatetime().getDateString());
		}
		if (!getDatetime().getTimeString().toString().isEmpty()) {
			builder.append(" Time: ").append(getDatetime().getTimeString());
		}
		if (!getTags().toString().isEmpty()) {
			builder.append(" Tags: ");
			getTags().forEach(builder::append);
		}
		return builder.toString();
	}

	/**
	 * Returns a string representation of this Person's tags
	 */
	default String tagsString() {
		final StringBuffer buffer = new StringBuffer();
		final String separator = ", ";
		getTags().forEach(tag -> buffer.append(tag).append(separator));
		if (buffer.length() == 0) {
			return "";
		} else {
			return buffer.substring(0, buffer.length() - separator.length());
		}
	}

}
