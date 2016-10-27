package seedu.task.commons.exceptions;

/**
 * Signals that some given data is empty.
 */

//@@author A0127570H
public class EmptyValueException extends Exception{

    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public EmptyValueException(String message) {
         super(message);
     }
}
