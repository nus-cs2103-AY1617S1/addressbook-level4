package seedu.task.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public abstract class DuplicateDataException extends IllegalValueException {

    private static final long serialVersionUID = -7473678765169569017L;

    public DuplicateDataException(String message) {
        super(message);
    }
}
