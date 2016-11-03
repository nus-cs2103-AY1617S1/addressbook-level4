//@@author A0142325R
package seedu.address.commons.exceptions;


/**
 * Signals that the recurring task added does not have a recurring date.
 */
public class MissingRecurringDateException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public MissingRecurringDateException(String message) {
        super(message);
    }
}

