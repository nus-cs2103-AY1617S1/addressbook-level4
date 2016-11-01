package seedu.gtd.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }
    
    //@@author A0146130W
    public DataConversionException(String cause) {
        super(cause);
    }
}
