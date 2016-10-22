package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

/**
 * Represents a single command parameter that the parser will try to feed the user 
 * input into
 */
public interface Parameter {
    void setValue(String input) throws IllegalValueException;
    
    boolean isPositional();
    
    boolean hasBoundValue();
    
    boolean isOptional();
    
    String getFlag();
    
    String getName();
    
    String getDescription();
    
    void checkRequired() throws IllegalValueException;
}
