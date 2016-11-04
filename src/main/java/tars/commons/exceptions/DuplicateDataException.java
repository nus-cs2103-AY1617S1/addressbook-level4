package tars.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public class DuplicateDataException extends IllegalValueException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
