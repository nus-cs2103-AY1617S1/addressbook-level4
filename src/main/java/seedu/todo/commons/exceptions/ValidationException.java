package seedu.todo.commons.exceptions;

import seedu.todo.model.ErrorBag;

public class ValidationException extends Exception {

    private ErrorBag errors;
    
    public ValidationException(String message) {
        super(message);
        this.errors = new ErrorBag();
    }
    
    public ValidationException(String message, ErrorBag errors) {
        super(message);
        this.errors = errors;
    }
    
    public ErrorBag getErrors() {
        return errors;
    }
}
