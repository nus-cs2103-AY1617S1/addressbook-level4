package harmony.mastermind.commons.exceptions;


/**
 * Signals that the start date is after end date for the Task of event type
 */
public class InvalidEventDateException extends Exception {

    public InvalidEventDateException() {
        super("Cannot create an event with start date that is after end date");
    }
}