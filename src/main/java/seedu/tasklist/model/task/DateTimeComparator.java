package seedu.tasklist.model.task;

/**
 * A immutable interface for comparing a Task's DateTime in the task list.
 */
interface DateTimeComparator {

    /**
     * Check if date time is after the compared DateTime.
     */
    boolean isDateTimeAfter(DateTime dateTime);

    /**
     * Check if date time is before current local time.
     */
    boolean isDateTimeAfterCurrentDateTime();

    boolean isDateTimeEmpty();
    boolean isDateEmpty();
    boolean isTimeEmpty();

}
