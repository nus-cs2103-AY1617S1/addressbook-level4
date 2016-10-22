package seedu.tasklist.model.task;

/**
 * A immutable interface for comparing a Task's DateTime in the task list.
 */
public interface DateTimeComparator {

    /**
     * Check if date time is after the compared DateTime.
     */
    public boolean isDateTimeAfter(DateTime dateTime);

    /**
     * Check if date time is before current local time.
     */
    public boolean isDateTimeAfterCurrentDateTime();

    public boolean isDateTimeEmpty();
    public boolean isDateEmpty();
    public boolean isTimeEmpty();

}
