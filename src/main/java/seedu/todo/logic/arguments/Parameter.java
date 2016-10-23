package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

//@@author A0135817B
/**
 * Represents a single command parameter that the parser will try to feed the user 
 * input into. The Parameter interface is needed because the Argument base class is 
 * typed, so this interface contains all of the non-typed methods that are common to 
 * all argument subclasses
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
