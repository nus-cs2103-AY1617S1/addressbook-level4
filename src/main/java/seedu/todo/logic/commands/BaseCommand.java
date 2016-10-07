package seedu.todo.logic.commands;

import java.util.Map.Entry;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.TodoModel;
import seedu.todo.model.task.ImmutableTask;

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
    
    protected void validateArguments() throws IllegalValueException {
        // Hook allowing subclasses to implement their own validation logic for arguments
    }
    
    /**
     * Binds the data model to the command object
     */
    public void setModel(TodoModel model) {
        this.model = model;
    }
    
    /**
     * Obtains the task at the specified index that is currently displayed to the 
     * user. The index is 1-indexed, as the list of tasks shown to the user starts 
     * at 1. 
     * 
     * @throws IllegalValueException if the task does not exist
     */
    public ImmutableTask getTaskAt(int index) throws IllegalValueException {
        try {
            return model.getObserveableList().get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalValueException("The specified index does not exist");
        }
    }
    
    /**
     * Binds the both positional and named command arguments from the parse results 
     * to the command object itself 
     * 
     * @throws IllegalValueException
     */
    public void setArguments(ParseResult arguments) throws IllegalValueException {
        if (arguments.getPositionalArgument().isPresent()) {
            setPositionalArgument(arguments.getPositionalArgument().get());
        }
        
        for (Entry<String, String> e : arguments.getNamedArguments().entrySet()) {
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
            if (!p.isOptional() && !p.hasBoundValue()) {
                // TODO: Deal with missing arguments
            }
        }
    }
}
