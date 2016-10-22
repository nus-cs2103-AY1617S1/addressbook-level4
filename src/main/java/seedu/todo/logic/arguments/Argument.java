package seedu.todo.logic.arguments;

import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.parser.TodoParser;

import java.util.logging.Logger;

//@@author A0135817B
abstract public class Argument<T> implements Parameter {
    private static final String REQUIRED_ERROR_FORMAT = "The %s parameter is required";
    private static final String TYPE_ERROR_FORMAT = "The %s should be a %s. You gave '%s'.";
    
    protected static final String OPTIONAL_ARGUMENT_FORMAT = "[%s]";
    protected static final String FLAG_ARGUMENT_FORMAT = "%s%s %s";
    
    private String name;
    private String description;
    private String flag;
    private boolean optional = true;
    private boolean boundValue = false;
    
    protected T value;
    
    private String requiredErrorMessage;
    
    private static final Logger logger = LogsCenter.getLogger(Argument.class);
    
    public Argument(String name) {
        this.name = name;
    }
    
    public Argument(String name, T defaultValue) {
        this.name = name;
        this.value = defaultValue;
    }
    
    /**
     * Binds a value to this parameter. Implementing classes MUST override AND 
     * call the parent class function so that the dirty bit is set for required
     * parameter validation to work
     */
    @Override
    public void setValue(String input) throws IllegalValueException {
        boundValue = true;
    }
    
    public T getValue() {
        return value;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Argument<T> description(String description) {
        this.description = description;
        return this;
    }

    public String getFlag() {
        return flag;
    }
    
    public Argument<T> flag(String flag) {
        this.flag = flag.trim().toLowerCase();
        
        if (!this.flag.equals(flag)) {
            logger.warning("Flag argument has uppercase or whitespace characters. These have been ignored.");
        }
        
        return this;
    }

    public boolean isOptional() {
        return optional;
    }
    
    public boolean hasBoundValue() {
        return boundValue;
    }
    
    /**
     * Sets the field as required
     */
    public Argument<T> required() {
        this.optional = false;
        return this;
    }
    
    /**
     * Sets the field as required and specify an error message to show if it is not provided
     * @param errorMessage shown to the user when the parameter is not provided 
     */
    public Argument<T> required(String errorMessage) {
        requiredErrorMessage = errorMessage;
        this.optional = false;
        return this;
    }
    
    @Override
    public boolean isPositional() {
        return flag == null;
    }
    
    @Override
    public void checkRequired() throws IllegalValueException {
        if (!isOptional() && !hasBoundValue()) {
            String error = requiredErrorMessage == null ? 
                    String.format(Argument.REQUIRED_ERROR_FORMAT, name) : requiredErrorMessage;
            throw new IllegalValueException(error);
        }
    }

    /**
     * Throws an IllegalValueException for a type mismatch between user input and what 
     * the argument expect 
     * @param field     name of the argument 
     * @param expected  the expected type for the argument 
     * @param actual    what the user actually gave 
     */
    protected void typeError(String field, String expected, String actual) throws IllegalValueException {
        throw new IllegalValueException(String.format(Argument.TYPE_ERROR_FORMAT, field, expected, actual));
    }
    
    @Override
    public String toString() {
        return toString(name);
    }
    
    public String toString(String name) {
        if (!isPositional()) {
            name = String.format(FLAG_ARGUMENT_FORMAT, TodoParser.FLAG_TOKEN, flag, name);
        }

        if (isOptional()) {
            name = String.format(OPTIONAL_ARGUMENT_FORMAT, name);
        }

        return name;
    }
}
