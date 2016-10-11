package seedu.ggist.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class TaskNotFoundException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}