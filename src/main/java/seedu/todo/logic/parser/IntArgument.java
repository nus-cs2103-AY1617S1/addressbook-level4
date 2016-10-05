package seedu.todo.logic.parser;

import seedu.todo.commons.exceptions.IllegalValueException;

public class IntArgument extends Argument<Integer> {

    public IntArgument(String name) {
        super(name);
    }
    
    public IntArgument(String name, int defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void setValue(String input) throws IllegalValueException {
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The argument");
        }
    }

}
