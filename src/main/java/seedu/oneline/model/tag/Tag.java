package seedu.oneline.model.tag;


import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.task.TaskRecurrence;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final Tag EMPTY_TAG = new Tag();
    
    public String tagName = null;

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
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns the default tag value
     */
    public static Tag getDefault() {
        return EMPTY_TAG;
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
