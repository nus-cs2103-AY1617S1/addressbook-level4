package seedu.whatnow.commons.exceptions;
//@@author A0139772U-reused
/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
