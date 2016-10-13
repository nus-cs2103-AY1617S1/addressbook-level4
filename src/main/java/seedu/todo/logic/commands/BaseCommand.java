package seedu.todo.logic.commands;

import java.util.Map.Entry;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.TodoModel;
import seedu.todo.model.task.ImmutableTask;

/**
 * The base class for commands. All commands need to implement an execute function 
 * and a getArguments function that collects the command arguments for the use of 
 * the help command. 
 * 
 * To perform additional validation on incoming arguments, override the validateArguments 
 * function.
 */
public abstract class BaseCommand {
    /**
     * The default message that accompanies argument errors
     */
    private static final String DEFAULT_ARGUMENT_ERROR_MESSAGE = ""; 
    
    private static final String TASK_MODIFIED_SUCCESS_MESSAGE = "'%s' successfully %s!";

    protected TodoModel model;
    
    protected ErrorBag errors = new ErrorBag(); 
    
    abstract protected Parameter[] getArguments();
    
    abstract public CommandResult execute() throws IllegalValueException;
    
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
     * @throws ValidationException if the arguments are invalid
     */
    public void setArguments(ParseResult arguments) throws ValidationException {
        if (arguments.getPositionalArgument().isPresent()) {
            setPositionalArgument(arguments.getPositionalArgument().get());
        }
        
        for (Entry<String, String> e : arguments.getNamedArguments().entrySet()) {
            setNameArgument(e.getKey(), e.getValue());
        }
        
        checkRequiredArguments();
        validateArguments();
        
        errors.validate(getArgumentErrorMessage());
    }
    
    /**
     * Hook allowing subclasses to implement their own validation logic for arguments
     * Subclasses should add additional errors to the errors ErrorBag
     */
    protected void validateArguments() {
        // Does no additional validation by default 
    }
    
    private void setPositionalArgument(String argument) {
        for (Parameter p : getArguments()) {
            if (p.isPositional()) {
                try {
                    p.setValue(argument);
                } catch (IllegalValueException e) {
                    errors.put(e.getMessage());
                }
            }
        }
    }
    
    private void setNameArgument(String flag, String argument) {
        for (Parameter p : getArguments()) {
            if (flag.equals(p.getFlag())) {
                try {
                    p.setValue(argument);
                } catch (IllegalValueException e) {
                    errors.put(p.getName(), e.getMessage());
                }
                
                return;
            }
        }
        
        // TODO: Do something for unrecognized argument?
    }
    
    private void checkRequiredArguments() {
        for (Parameter p : getArguments()) {
            try {
                p.checkRequired();
            } catch (IllegalValueException e) {
                errors.put(p.getName(), e.getMessage());
            }
        }
    }

    /**
     * Override this function if the command should return some other error 
     * message on argument validation error 
     */
    protected String getArgumentErrorMessage() {
        return BaseCommand.DEFAULT_ARGUMENT_ERROR_MESSAGE;
    }

    /**
     * Returns a generic CommandResult with a "{task} successfully {verbed}" success message. 
     * 
     * @param title the title of the task that was verbed on 
     * @param verb the action that was performed on the task, in past tense
     */
    protected CommandResult taskSuccessfulResult(String title, String verb) {
        return new CommandResult(String.format(BaseCommand.TASK_MODIFIED_SUCCESS_MESSAGE, title, verb));
    }
}
