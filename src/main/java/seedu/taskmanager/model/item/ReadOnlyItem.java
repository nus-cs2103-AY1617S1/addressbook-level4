//@@author A0140060A
package seedu.taskmanager.model.item;

import seedu.taskmanager.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Item in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 * Date and Time can be empty strings.
 */
public interface ReadOnlyItem {

    ItemType getItemType();
    Name getName();
    ItemDate getStartDate();
    ItemTime getStartTime();
    ItemDate getEndDate();
    ItemTime getEndTime();
    boolean getDone();
    void setDone();
    void setUndone();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyItem other) {
        return other != null // this is first to avoid NPE below
                && other.getItemType().equals(this.getItemType()) // state checks here onwards
                && other.getName().equals(this.getName())
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime())
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getDone() == this.getDone()
                && other.getTags().equals(this.getTags());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getItemType())
               .append("\n")
               .append("Name: ")
               .append(getName());
        if (getItemType().toString().equals(ItemType.EVENT_WORD)) {
            builder.append("\n")
                   .append("Start Date: ")
                   .append(getStartDate())
                   .append(" Start time: ")
                   .append(getStartTime());
        }
        if (getItemType().toString().equals(ItemType.DEADLINE_WORD) || getItemType().toString().equals(ItemType.EVENT_WORD)) {
            builder.append("\n")
                   .append("End Date: ")
                   .append(getEndDate())
                   .append(" End time: ")
                   .append(getEndTime());
        }
        builder.append("\n").append("Tags: ");
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
