package tars.commons.exceptions;

/**
 * Signals invalid range entered.
 */
public class InvalidRangeException extends Exception {
    public static final String MESSAGE_INVALID_RANGE =
            "Start index must be before end index.";

    public InvalidRangeException() {
        super(MESSAGE_INVALID_RANGE);
    }
}
