package seedu.todo.commons.exceptions;

public class AmbiguousEventTypeException extends ParseException {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public AmbiguousEventTypeException(String message) {
        super(message);
    }
}
