package seedu.oneline.model.tag;

import seedu.oneline.commons.exceptions.IllegalValueException;

public class TagColor {

    public static final String MESSAGE_COLOR_CONSTRAINTS = "Valid colors: <red, blue, green>";
    
    public TagColor(String color) throws IllegalValueException {
        if (!isValidColor(color)) {
            throw new IllegalValueException(MESSAGE_COLOR_CONSTRAINTS);
        }
    }
    
    public static boolean isValidColor(String color) {
        return true;
    }
    
}
