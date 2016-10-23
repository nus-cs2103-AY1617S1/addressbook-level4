package seedu.todo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.todo.commons.exceptions.ValidationException;

//@@author A0135817B
public class ErrorBag {
    private List<String> nonFieldErrors = new ArrayList<>();
    private Map<String, String> fieldErrors = new HashMap<>();
    
    /**
     * Add an error that is not related to any specific field
     * 
     * @param nonFieldError the error message
     */
    public void put(String nonFieldError) {
        nonFieldErrors.add(nonFieldError);
    }
    
    /**
     * Add an error that is related to a specific field 
     * 
     * @param field the error is for
     * @param error the error message 
     */
    public void put(String field, String error) {
        fieldErrors.put(field, error);
    }
    
    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    
    public int size() {
        return nonFieldErrors.size() + fieldErrors.size();
    }
    
    /**
     * Throws a validation exception if the bag contains errors 
     * 
     * @param message a short message about why the validation failed
     * @throws ValidationException if the ErrorBag is not empty
     */
    public void validate(String message) throws ValidationException {
        if (size() > 0) {
            throw new ValidationException(message, this);
        }
    }
}
