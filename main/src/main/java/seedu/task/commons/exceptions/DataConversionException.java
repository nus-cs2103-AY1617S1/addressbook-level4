package seedu.task.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {

    private static final long serialVersionUID = -2556905669265883497L;

    public DataConversionException(Exception cause) {
        super(cause);
    }

}
