package seedu.address.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IncorrectCommandException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IncorrectCommandException(String message) {
        super(message);
    }
}
