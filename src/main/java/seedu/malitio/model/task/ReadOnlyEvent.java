package seedu.malitio.model.task;
import seedu.malitio.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Deadline in Malitio.
 * Implementations should guarantee: details are present and not null, field values are validated.
 * @@ Annabel Eng A0129595N
 * 
 */

public interface ReadOnlyEvent {

        Name getName();
        DateTime getStart();
        DateTime getEnd();
        
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
                    && other.getName().equals(this.getName()) 
                    && other.getStart().toString().equals(this.getStart().toString())
                    && other.getEnd().toString().equals(this.getEnd().toString())//state checks here onwards
    );
        }

        /**
         * Formats the event as text, showing all contact details.
         */
        default String getAsText() {
            final StringBuilder builder = new StringBuilder();
            builder.append(getName())
                    .append(" ")
                    .append(getStart())
                    .append(" ")
                    .append(getEnd())
                    .append(" Tags: ");
            getTags().forEach(builder::append);
            return builder.toString();
        }

        /**
         * Returns a string representation of this Event's tags
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

