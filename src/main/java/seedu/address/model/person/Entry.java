package seedu.address.model.person;

import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for an Entry in the Task Manager.
 * Implementations should guarantee: details are present and not null, field
 * values are validated.
 */
public interface Entry {

    Title getTitle();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    boolean isSameStateAs(Entry other);

    /**
     * Formats the entry as text, showing all contact details.
     */
    String getAsText();

    /**
     * Returns a string representation of this Entry's tags
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
     * Sets the Tags for this Entry
     */
    void setTags(UniqueTagList uniqueTagList);

    /**
     * Sets the Title for this Entry
     */
    void setTitle(Title newTitle);

}
