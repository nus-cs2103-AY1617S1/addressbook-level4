package seedu.ggist.model.task;

import java.util.Date;

import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * A read-only immutable (except for setting task done) interface for a Task in the task manager.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    TaskName getTaskName();
    TaskDate getStartDate();
    TaskTime getStartTime();
    TaskDate getEndDate();
    TaskTime getEndTime();
    Priority getPriority();
    Date getStartDateTime();
    Date getEndDateTime();
    boolean isDone();
    boolean isDeleted();
    void constructStartDateTime(TaskDate date, TaskTime time) throws IllegalValueException;
    void constructEndDateTime(TaskDate date, TaskTime time) throws IllegalValueException;

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTaskName().equals(this.getTaskName()) // state checks here onwards
                && other.getStartDate().equals(this.getStartDate())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndDate().equals(this.getEndDate())
                && other.getEndTime().equals(this.getEndTime())
                && isDone() == this.isDone()
                && isDeleted() == this.isDeleted()
                && other.getPriority().equals(this.getPriority())
         );
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" Task Name: ")
                .append(getTaskName())
                .append(" Start Date: ")
                .append(getStartDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Date: ")
                .append(getEndDate())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Priority: ")
                .append(getPriority());
        return builder.toString();
    }
    void setDone();
    void setUnDone();

}
