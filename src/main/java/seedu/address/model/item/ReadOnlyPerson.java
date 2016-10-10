package seedu.address.model.item;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Item in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ItemType getItemType();
    Name getName();
    Date getStartDate();
    Time getStartTime();
    Date getEndDate();
    Time getEndTime();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getItemType().equals(this.getItemType()) // state checks here onwards
                && other.getName().equals(this.getName())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getItemType())
                .append(" Name: ")
                .append(getName())
                .append(" Email: ")
                .append(getEndDate())
                .append(" Address: ")
                .append(getEndTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Item's tags
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
