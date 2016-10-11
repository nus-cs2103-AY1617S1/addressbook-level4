package tars.commons.exceptions;

/**
 * Signals that an operation would have violated the 'no duplicates' property of the list.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate tasks");
    }
}
