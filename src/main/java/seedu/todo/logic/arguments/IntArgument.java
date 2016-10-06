package seedu.todo.logic.arguments;

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
            super.setValue(input);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("The argument");
        }
    }

}
