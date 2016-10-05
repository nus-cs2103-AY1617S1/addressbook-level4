package seedu.todo.logic.parser;

import seedu.todo.commons.exceptions.IllegalValueException;

/**
 * Represents a single command parameter that the parser will try to feed the user 
 * input into
 */
public interface Parameter {
    public void setValue(String input) throws IllegalValueException;
    
    public boolean isPositional();
    
    public boolean isSet();
    
    public boolean isOptional();
    
    public String getFlag();
    
    public String getName();
    
    public String getDescription();
}
