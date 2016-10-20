package seedu.oneline.model.tag;


import seedu.oneline.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public String tagName;
    private String colour; 

    public Tag() {
    }
    
    //@@author A0142605N
    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTagName(name)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS + " : " + name);
        }
        this.tagName = name;
        this.colour = "#ffffff";
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }
    
    public void setColour(String colour) {
        switch(colour) {
        case "white": 
            this.colour = "#ffffff";
            break;
        case "red": 
            this.colour = "#F95E59"; 
            break;
        case "orange": 
            this.colour = "#ff6633"; 
            break;
        case "yellow": 
            this.colour = "#FEE715"; 
            break;
        case "green": 
            this.colour = "#F95E59"; 
            break;
        case "blue": 
            this.colour = "#F95E59"; 
            break;
        case "purple": 
            this.colour = "#F95E59"; 
            break;
        default: 
            return; 
        }
            
    }
    
    public String getColour() {
        return colour; 
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }
    
    /**
     * Serialize field for storage
     */
    public String serialize() {
        return tagName;
    }
    
    /**
     * Deserialize from storage
     */
    public static Tag deserialize(String args) throws IllegalValueException {
        return new Tag(args);
    }

}
