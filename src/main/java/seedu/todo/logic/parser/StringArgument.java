package seedu.todo.logic.parser;

import seedu.todo.commons.exceptions.IllegalValueException;

public class StringArgument extends Argument<String> {

    public StringArgument(String name) {
        super(name);
    }
    
    public StringArgument(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void setValue(String input) throws IllegalValueException {
        this.value = input;
    }

}
