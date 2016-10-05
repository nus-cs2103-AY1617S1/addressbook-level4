package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

public class FlagArgument extends Argument<Boolean> {

    public FlagArgument(String name) {
        super(name);
        this.value = false;
    }

    public FlagArgument(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void setValue(String input) throws IllegalValueException {
        this.value = true;
        super.setValue(input);
    }
    
}
