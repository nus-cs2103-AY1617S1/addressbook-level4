package seedu.address.model.task;

import java.util.Date;

import seedu.address.commons.util.DateFormatter;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Date getStartDate();
    Date getEndDate();
    Location getLocation();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getEndDate().equals(this.getEndDate())
                && other.getLocation().equals(this.getLocation()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start Date: ")
                .append(DateFormatter.convertDateToDisplayString(getStartDate()))
                .append(" Due Date: ")
                .append(DateFormatter.convertDateToDisplayString(getEndDate()))
                .append(" Location: ")
                .append(getLocation())
                .append(" Tags: ");
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
    
    /**  
     * For FindCommand to Formats the person as text,   
     * showing all contact details.  
     */  
    default String getFindAsText() {  
        final StringBuilder builder = new StringBuilder();  
        builder.append(getName())  
            .append(" ")  
            .append(DateFormatter.convertDateToString(getStartDate()))  
            .append(" ")  
            .append(DateFormatter.convertDateToString(getEndDate()))  
            .append(" ")  
            .append(getLocation())  
            .append(" ");  
        getTags().forEach(builder::append);  
        return builder.toString();  
    }  


}
