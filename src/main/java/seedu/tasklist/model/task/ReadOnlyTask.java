package seedu.tasklist.model.task;

import java.util.Calendar;
import java.util.Date;

/**
 * A read-only immutable interface for a Task in the task list.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {
	TaskDetails getTaskDetails();
    StartTime getStartTime();
    EndTime getEndTime();
    Priority getPriority();
    String getRecurringFrequency();
    int getUniqueID();
    boolean isFloating();
    boolean isOverDue();
    boolean isComplete();
    boolean isRecurring();
	boolean isToday();
	boolean isTomorrow();
	boolean isEvent();
	boolean equals(Task task);
	

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     *//*
    UniqueTagList getTags();*/

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskDetails().equals(this.getTaskDetails()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getPriority().equals(this.getPriority())
                && (other.isRecurring() == this.isRecurring())
                && other.getRecurringFrequency().equals(this.getRecurringFrequency())
                && (other.getUniqueID()==this.getUniqueID()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskDetails()+ "\n")
                .append("Starts:\t")
                .append(getStartTime().toCardString() + "\t")
                .append("Ends:\t")
                .append(getEndTime().toCardString()+ "\n")
                .append("Priority:\t")
                .append(getPriority()+ "\n");
     //   getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Person's tags
     */
    
   /* default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }*/

    /**
     * Updates the dates of a task based on its recurring frequency.
     */
    default Calendar updateRecurringDate(Calendar toUpdate, String frequency, int value) {
        if (!toUpdate.getTime().equals(new Date(0))) {
            switch (frequency) {
                case "daily": toUpdate.add(Calendar.DAY_OF_YEAR, value); break;
                case "weekly": toUpdate.add(Calendar.WEEK_OF_YEAR, value); break;
                case "monthly": toUpdate.add(Calendar.MONTH, value); break;
                case "yearly": toUpdate.add(Calendar.YEAR, value); break;
            }
        }
        return toUpdate;
    }
    
    /**
     * Checks if the date of the task matches user requested date based on its recurring frequency.
     */
    default boolean recurringMatchesRequestedDate(Calendar task, String frequency, Calendar requested) {
        if (!task.getTime().equals(new Date(0)) && !requested.getTime().equals(new Date (0))
                && (frequency.equals("daily")
                    || (frequency.equals("weekly") && task.get(Calendar.DAY_OF_WEEK) == requested.get(Calendar.DAY_OF_WEEK))
                    || (frequency.equals("monthly") && task.get(Calendar.DAY_OF_MONTH) == requested.get(Calendar.DAY_OF_MONTH))
                    || (frequency.equals("yearly") && task.get(Calendar.DAY_OF_YEAR) == requested.get(Calendar.DAY_OF_YEAR)))) {
                return true;
        }
        return false;
    }

}