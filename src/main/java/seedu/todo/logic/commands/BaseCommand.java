package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.parser.ParseResult;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.Model;

import java.util.List;
import java.util.Map.Entry;
import java.util.StringJoiner;

//@@author A0135817B
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

    protected Model model;
    
    protected ErrorBag errors = new ErrorBag(); 
    
    abstract protected Parameter[] getArguments();

    /**
     * Return the name of the command, which is used to call it
     */
    abstract public String getCommandName();

    /**
     * Returns a list of command summaries for the command. This function returns a 
     * list because commands may (rarely) be responsible for more than one thing, 
     * like the <code>add</code> command. 
     */
    abstract public List<CommandSummary> getCommandSummary();
    
    abstract public CommandResult execute() throws ValidationException;
    
    /**
     * Binds the data model to the command object
     */
    public void setModel(Model model) {
        this.model = model;
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
    
    protected void setPositionalArgument(String argument) {
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
    
    protected void setNameArgument(String flag, String argument) {
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

    /**
     * Turns the arguments into a string summary using their toString function
     */
    protected String getArgumentSummary() {
        StringJoiner sj = new StringJoiner(" ");
        for (Parameter p : getArguments()) {
            sj.add(p.toString());
        }
        return sj.toString();
    }
}
