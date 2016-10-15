package seedu.todo.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class UnmatchedQuotesException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public UnmatchedQuotesException(String message) {
        super(message);
    }
}
