package seedu.savvytasker.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public abstract class DuplicateDataException extends IllegalValueException {
    /**
     * Generated serial
     */
    private static final long serialVersionUID = 5045611929659070003L;

    public DuplicateDataException(String message) {
        super(message);
    }
}
