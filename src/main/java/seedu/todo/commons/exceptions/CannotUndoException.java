package seedu.todo.commons.exceptions;

public class CannotUndoException extends Exception {
    public CannotUndoException(Exception cause) {
        super(cause);
    }
}