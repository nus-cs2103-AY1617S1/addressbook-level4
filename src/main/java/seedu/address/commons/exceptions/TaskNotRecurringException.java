package seedu.address.commons.exceptions;

//@@author A0139655U
/**
 * Signals that task does not have recurrence rate or does not have both start and end dates.
 */
public class TaskNotRecurringException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TaskNotRecurringException(String message) {
        super(message);
    }
}
