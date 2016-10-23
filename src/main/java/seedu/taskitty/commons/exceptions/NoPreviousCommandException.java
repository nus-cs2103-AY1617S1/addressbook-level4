package seedu.taskitty.commons.exceptions;

/**
 * Signals that there is no more previous commands in the current session
 */
public class NoPreviousCommandException extends Exception {
    
    public NoPreviousCommandException(String message) {
        super(message);
    }
}
