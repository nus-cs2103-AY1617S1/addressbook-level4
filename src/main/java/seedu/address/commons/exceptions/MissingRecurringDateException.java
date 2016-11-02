//@@author A0142325R
package seedu.address.commons.exceptions;


/**
 * Signals that some given data does not fulfill some constraints.
 */
public class MissingRecurringDateException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public MissingRecurringDateException(String message) {
        super(message);
    }
}

