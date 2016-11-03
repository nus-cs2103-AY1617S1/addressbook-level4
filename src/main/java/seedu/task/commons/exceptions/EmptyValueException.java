package seedu.task.commons.exceptions;


//@@author A0127570H
/**
 * Signals that some given data is empty.
 */

public class EmptyValueException extends Exception{

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public EmptyValueException(String message) {
         super(message);
     }
}
