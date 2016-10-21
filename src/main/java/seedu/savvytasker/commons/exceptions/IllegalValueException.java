package seedu.savvytasker.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class IllegalValueException extends Exception {
    /**
     * Generated serial
     */
    private static final long serialVersionUID = 193885632897265498L;

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public IllegalValueException(String message) {
        super(message);
    }
}
