//@@author A0140156R

package seedu.oneline.commons.exceptions;

public class IncorrectCommandException extends Exception {
    /**
     * @param message should contain relevant information on the failed arguments(s)
     */
    public IncorrectCommandException(String message) {
        super(message);
    }
}
