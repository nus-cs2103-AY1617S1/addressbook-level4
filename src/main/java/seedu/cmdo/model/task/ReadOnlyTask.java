package seedu.cmdo.model.task;

import seedu.cmdo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in the To Do List.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask extends Cloneable {
	
    Detail getDetail();
    Done checkDone();
    DueByDate getDueByDate();
    DueByTime getDueByTime();
    Priority getPriority();
	boolean getBlock();
	boolean isRange();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the task's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
           
                && other.getDetail().details.equals(this.getDetail().details) // state checks here onwards
                && other.checkDone().value.equals(this.checkDone().value)
                && other.getDueByDate().toString().equals(this.getDueByDate().toString())
                && other.getDueByTime().toString().equals(this.getDueByTime().toString())
                && other.getPriority().value.equals(this.getPriority().value)
                && other.getBlock() == (this.getBlock()));
    }

//    /**
//     * Formats the task as text, showing all contact details.
//     */
//    default String getAsText() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append(getDetail())
//                .append(" due on ")
//                .append(getDueByDate())
//                .append(" due at: ")
//                .append(getDueByTime())
//                .append(" with ")
//                .append(getPriority())
//                .append(" priority.")
//                .append(" Tags applied: ")
//        		.append(" Done? ")
//        		.append(checkDone().value ? "yes" : "no");
//        getTags().forEach(builder::append);
//        return builder.toString();
//    }

  /**
   * Formats the task as text, showing all contact details.
   */
  default String getAsText() {
	    return getDetail().details;
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
    /*@@author A0141128R
     * To clone task
     */
    public Task makeCopy();

}
