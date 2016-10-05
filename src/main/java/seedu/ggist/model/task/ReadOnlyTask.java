package seedu.ggist.model.task;

import seedu.ggist.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

<<<<<<< HEAD:src/main/java/seedu/address/model/person/ReadOnlyPerson.java
    Name getName();
=======
    TaskName getTaskName();
>>>>>>> 2196a4f91cbd3b9663c1ef7ca7f3551168fa35e4:src/main/java/seedu/ggist/model/task/ReadOnlyTask.java
    Date getDate();
    Time getTime();
    Priority getPriority();
    Frequency getFrequency();

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
                && other.getDate().equals(this.getDate())
                && other.getTime().equals(this.getTime())
                && other.getPriority().equals(this.getPriority()))
                && other.getFrequency().equals(this.getFrequency()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Frequency: ")
                .append(getFrequency())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
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
