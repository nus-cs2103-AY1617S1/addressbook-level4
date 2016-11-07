package seedu.dailyplanner.model.task;

import java.util.Date;

import seedu.dailyplanner.model.category.UniqueCategoryList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

   

    String getName();
    DateTime getStart();
    DateTime getEnd();
    String getCompletion();
    String getDueStatus();
    boolean isPinned();
    boolean isComplete();
    
    void setName(String name);
    void setStart(DateTime start);
    void setEnd(DateTime end);
    void markAsComplete();
    void markAsNotComplete();
    void pin();
    void unpin();
    void setCompletion(boolean completion);

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueCategoryList getCats();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStart().equals(this.getStart())
                && other.getEnd().equals(this.getEnd())
        		&& other.getCompletion().equals(this.getCompletion())
        		&& other.isPinned() == this.isPinned());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start: ")
                .append(getStart())
                .append(" End: ")
                .append(getEnd())
                .append(" Tags: ");
        getCats().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Person's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getCats().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }
   
    
}
