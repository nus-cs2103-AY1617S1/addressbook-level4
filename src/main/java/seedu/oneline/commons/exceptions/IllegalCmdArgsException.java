//@@author A0140156R

package seedu.oneline.commons.exceptions;

public class IllegalCmdArgsException extends Exception {
    /**
     * @param message should contain relevant information on the failed arguments(s)
     */
    public IllegalCmdArgsException(String message) {
        super(message);
    }
}
