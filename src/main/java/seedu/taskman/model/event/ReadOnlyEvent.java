package seedu.taskman.model.event;

import seedu.taskman.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Event in the TaskMan.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Title getTitle();
    boolean isRecurring();
    boolean isScheduled();
    Frequency getFrequency();
    Schedule getSchedule();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the event's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle()) // state checks here onwards
                && other.isRecurring() == this.isRecurring()
        		&& other.isScheduled() == this.isScheduled()
				&& other.getFrequency().equals(this.getFrequency())
        		&& other.getSchedule().equals(this.getSchedule())
                );
    }

    /**
     * Formats the event as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
		        .append(" Recurring: ")
			    .append(isRecurring())	    
		        .append(" Scheduled: ")
		        .append(getSchedule())
		        .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this event's tags
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
