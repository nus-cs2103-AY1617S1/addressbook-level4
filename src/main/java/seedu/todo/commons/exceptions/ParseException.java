package seedu.todo.commons.exceptions;

public class ParseException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public ParseException(String message) {
        super(message);
    }
}
