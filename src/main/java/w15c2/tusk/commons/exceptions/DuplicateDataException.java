package w15c2.tusk.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public abstract class DuplicateDataException extends IllegalValueException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
