package seedu.todo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidNaturalDateException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidNaturalDateException(String message) {
        super(message);
    }
}
