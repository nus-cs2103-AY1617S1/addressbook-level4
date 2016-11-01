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
        put("red", "#F48687");
        put("orange", "#FFB764");
        put("yellow", "#FBD75B"); 
        put("green", "#B1DE7A");
        put("blue", "#9AD0E5");
        put("purple", "#C89EE8");
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
    
    public String toLighterHTMLColor() {
        return lightenColor(toHTMLColor());
    }
    
    private static String lightenColor(String color) {
        int r = Integer.parseInt(color.substring(1, 3), 16);
        int g = Integer.parseInt(color.substring(3, 5), 16);
        int b = Integer.parseInt(color.substring(5, 7), 16);
        double ratio = 0.3;
        r = (int) Math.round(ratio * r + (1 - ratio) * 255);
        g = (int) Math.round(ratio * g + (1 - ratio) * 255);
        b = (int) Math.round(ratio * b + (1 - ratio) * 255);

        return "#" + Integer.toString(r, 16) + Integer.toString(g, 16) + Integer.toString(b, 16);
    }
    
}
