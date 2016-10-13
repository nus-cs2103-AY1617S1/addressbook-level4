package seedu.address.model.item;

import java.util.Date;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the todo list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyItem {

	Description getDescription();

	
	/**
	 * These return the start and end dates of the item (if they exist). If not,
	 * then the default null is used. Override these in the implemented classes
	 * as necessary.
	 */
	default Date getStartDate() {
		return null;
	}

	default Date getDueDate() {
		return null;
	}
	
    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
	default boolean isSameStateAs(ReadOnlyItem other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
						// state checks here onwards
                && other.getDescription().equals(this.getDescription()) 
                && other.getStartDate().equals(this.getStartDate())
                && other.getDueDate().equals(this.getDueDate()));
    }

    /**
     * Formats the Item as text, showing just the description
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
		builder.append(getDescription());
//                .append(" Phone: ")
//                .append(getPhone())
//                .append(" Email: ")
//                .append(getEmail())
//                .append(" Address: ")
//                .append(getAddress())
//                .append(" Tags: ");
        getTags().forEach(builder::append);
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
