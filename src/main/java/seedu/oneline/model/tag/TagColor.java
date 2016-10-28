package seedu.oneline.model.tag;

import java.util.HashMap; 
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.task.TaskName;


public class TagColor {

    public static final String EMPTY_COLOR_VALUE = "";
    
    private String value;
    private static final HashMap<String, String> colorValues = new HashMap<String, String>() {{
        put("", "#FFFFFF");
        put("white", "#FFFFFF");
        put("red", "#FF9D93");
        put("orange", "#FFBE76");
        put("yellow", "#FFEE90"); 
        put("green", "#B2EA76");
        put("blue", "#AAC3E2");
        put("purple", "#CAB8DF");
    }}; 
    
    public static final String MESSAGE_COLOR_CONSTRAINTS = "Valid colors: <white, red, orange, yellow, green, blue, purple>";
    
    public TagColor(String color) throws IllegalValueException {
        if (!isValidColor(color)) {
            throw new IllegalValueException(MESSAGE_COLOR_CONSTRAINTS);
        }
        this.value = color;
    }
    
    public static boolean isValidColor(String color) {
        return colorValues.containsKey(color);
    }
    
    public static TagColor getDefault() {
        try {
            return new TagColor(EMPTY_COLOR_VALUE);
        } catch (IllegalValueException e) {
            assert false; // This function should return a correct value!
        }
        return null;
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return value;
    }
    
    /**
     * Deserialize from storage
     */
    public static TagColor deserialize(String args) throws IllegalValueException {
        return new TagColor(args);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColor // instanceof handles nulls
                && this.value.equals(((TagColor) other).value)); // state check
    }
    
    public String toString() {
        return value;
    }
    
    public String toHTMLColor() {
        return colorValues.get(value);
    }
    
}
