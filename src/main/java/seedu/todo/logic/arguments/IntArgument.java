package seedu.todo.logic.arguments;

import seedu.todo.commons.exceptions.IllegalValueException;

//@@author A0135817B
public class IntArgument extends Argument<Integer> {

    public IntArgument(String name) {
        super(name);
    }
    
    @Override
    public void setValue(String input) throws IllegalValueException {
        try {
            value = Integer.parseInt(input);
            super.setValue(input);
        } catch (NumberFormatException e) {
            typeError(this.getName(), "integer", input);
        }
    }

}
