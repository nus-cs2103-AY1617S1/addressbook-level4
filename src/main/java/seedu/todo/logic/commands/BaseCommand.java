package seedu.todo.logic.commands;

import java.util.Map.Entry;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.TodoModel;

/**
 * The base class for commands. All commands need to implement an execute function 
 * and a getArguments function that collects the command arguments for the use of 
 * the help command. 
 * 
 * To perform additional validation on incoming arguments, override the validateArguments 
 * function.
 *
 */
public abstract class BaseCommand {
    protected TodoModel model;
    
    abstract protected Parameter[] getArguments();
    
    abstract public void execute() throws IllegalValueException;
    
    /**
     * Hook allowing subclasses to implement their own validation logic for arguments
     */
    protected void validateArguments() throws IllegalValueException { 
    }
    
    public void setModel(TodoModel model) {
        this.model = model;
    }
    
    public void setArguments(ParseResult parser) throws IllegalValueException {
        if (parser.getPositionalArgument().isPresent()) {
            setPositionalArgument(parser.getPositionalArgument().get());
        }
        
        for (Entry<String, String> e : parser.getNamedArguments().entrySet()) {
            setNameArgument(e.getKey(), e.getValue());
        }
        
        checkRequiredArguments();
        validateArguments();
    }
    
    private void setPositionalArgument(String argument) throws IllegalValueException {
        for (Parameter p : getArguments()) {
            if (p.isPositional()) {
                p.setValue(argument);
            }
        }
        
        // TODO: Do something for unrecognized argument
    }
    
    private void setNameArgument(String flag, String argument) throws IllegalValueException {
        for (Parameter p : getArguments()) {
            if (flag.equals(p.getFlag())) {
                p.setValue(argument);
            }
        }
        
        // TODO: Do something for unrecognized argument
    }
    
    private void checkRequiredArguments() {
        for (Parameter p : getArguments()) {
            if (!p.isOptional() && !p.isSet()) {
                // TODO: Deal with missing arguments
            }
        }
    }
}
