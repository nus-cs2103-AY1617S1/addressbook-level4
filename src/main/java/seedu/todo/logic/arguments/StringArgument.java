package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

//@@author A0135817B
public class StringArgument extends Argument<String> {

    public StringArgument(String name) {
        super(name);
    }
    
    @Override
    public void setValue(String input) throws IllegalValueException {
        input = input.trim();
        
        // Ignore empty strings
        if (input.length() > 0) {
            this.value = input;
        }
        
        super.setValue(input);
    }

}
