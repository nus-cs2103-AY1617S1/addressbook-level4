package seedu.jimi.logic;

@SuppressWarnings("serial")
public class NoRedoableOperationException extends RuntimeException {

    public NoRedoableOperationException(String msg) {
        super(msg);
    }

}
