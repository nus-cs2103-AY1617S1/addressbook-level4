package seedu.taskitty.commons.exceptions;

/**
 * Signals that there is no more previous commands in the current session
 */
public class NoPreviousValidCommandException extends Exception {
    
    public NoPreviousValidCommandException(String message) {
        super(message);
    }
}
