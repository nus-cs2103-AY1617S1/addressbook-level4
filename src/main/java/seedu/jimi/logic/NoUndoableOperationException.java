package seedu.jimi.logic;

@SuppressWarnings("serial")
public class NoUndoableOperationException extends RuntimeException {

    public NoUndoableOperationException(String msg) {
        super(msg);
    }
}
