package seedu.cmdo.commons.exceptions;

//@@author A0139661Y
/**
 * Signals that some given data does not fulfill some constraints.
 */
public class TaskBlockedException extends Exception {

    public TaskBlockedException(String message) {
        super(message);
    }
}
