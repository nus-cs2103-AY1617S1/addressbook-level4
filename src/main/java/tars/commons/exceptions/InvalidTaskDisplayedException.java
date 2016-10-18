package tars.commons.exceptions;

/**
 * Signals an index exceed the size of the internal list.
 */
public class InvalidTaskDisplayedException extends Exception {
    public InvalidTaskDisplayedException(String message) {
        super(message);
    }
}
