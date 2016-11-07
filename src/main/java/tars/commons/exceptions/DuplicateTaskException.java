package tars.commons.exceptions;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateTaskException extends DuplicateDataException {
    private static final String MESSAGE_DUPLICATE_TASK_ERROR =
            "Operation would result in duplicate tasks";

    public DuplicateTaskException() {
        super(MESSAGE_DUPLICATE_TASK_ERROR);
    }
}
