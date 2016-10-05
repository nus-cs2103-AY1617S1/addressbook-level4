package seedu.todo.logic.commands;

import java.util.Map.Entry;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.logic.arguments.Parameter;
import seedu.todo.logic.parser.ParseResult;

public abstract class BaseCommand {
    abstract protected Parameter[] getArguments();
    
    abstract public void execute();
    
    public void setArguments(ParseResult parser) throws IllegalValueException {
        if (parser.getPositionalArgument().isPresent()) {
            setPositionalArgument(parser.getPositionalArgument().get());
        }
        
        for (Entry<String, String> e : parser.getNamedArguments().entrySet()) {
            setNameArgument(e.getKey(), e.getValue());
        }
        
        checkRequiredArguments();
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
