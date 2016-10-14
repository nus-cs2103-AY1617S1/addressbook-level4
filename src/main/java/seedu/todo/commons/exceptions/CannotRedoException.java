package seedu.todo.commons.exceptions;

public class CannotRedoException extends Exception {
    public CannotRedoException(Exception cause) {
        super(cause);
    }
}