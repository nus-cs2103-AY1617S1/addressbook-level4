package seedu.cmdo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class TaskBlockedException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TaskBlockedException(String message) {
        super(message);
    }
}
