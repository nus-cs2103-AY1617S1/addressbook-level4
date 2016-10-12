package seedu.address.commons.exceptions;

/**
 * Signals an error caused by not existing data where there should be.
 */
public abstract class NotExistDataException extends IllegalValueException {
    public NotExistDataException(String message) {
        super(message);
    }
}
