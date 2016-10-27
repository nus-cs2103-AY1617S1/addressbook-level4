package seedu.taskitty.commons.exceptions;

/**
 * Signals that there is no more previous commands in the current session
 */
public class NoRecentUndoCommandException extends Exception {
    
    public NoRecentUndoCommandException(String message) {
        super(message);
    }
}
