//@@author A0139772U-reused
package seedu.whatnow.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
