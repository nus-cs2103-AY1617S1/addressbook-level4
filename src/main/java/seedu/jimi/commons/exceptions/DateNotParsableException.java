package seedu.jimi.commons.exceptions;

/**
 * Indicates the given text cannot be parsed to a date
 * @@author A0148040R
 *
 */
public class DateNotParsableException extends IllegalValueException {

    public DateNotParsableException(String message) {
        super(message);
    }

}
