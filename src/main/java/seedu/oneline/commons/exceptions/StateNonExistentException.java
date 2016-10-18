package seedu.oneline.commons.exceptions;

public class StateNonExistentException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public StateNonExistentException(String message) {
        super(message);
    }

}
