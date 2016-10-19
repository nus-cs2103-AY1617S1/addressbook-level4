package seedu.agendum.commons.exceptions;

/**
 * Represents an error during deletion of a file
 */
public class FileDeletionException extends Exception {
    public FileDeletionException(String message) {
        super(message);
    }
}
