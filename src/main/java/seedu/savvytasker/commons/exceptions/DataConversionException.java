package seedu.savvytasker.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    /**
     * Generated serial
     */
    private static final long serialVersionUID = -5908631495910190437L;

    public DataConversionException(Exception cause) {
        super(cause);
    }

}
