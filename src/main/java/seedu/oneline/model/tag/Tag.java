//@@author A0140156R
package seedu.oneline.model.tag;


import java.util.HashMap;
import java.util.Map;

import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.model.task.TaskRecurrence;

/**
 * Represents a Tag in the Task book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String EMPTY_TAG_VALUE = "#"; // escape character
    
    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final Tag EMPTY_TAG = createEmptyTag();
    
    public String tagName = "";

    public static final Map<String, Tag> allTags = new HashMap<String, Tag>();
    
    private Tag() {}
    
    private static Tag createEmptyTag() {
        Tag t = new Tag();
        t.tagName = EMPTY_TAG_VALUE;
        return t;
    }
    
    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    private Tag(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTagName(name)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS + " : " + name);
        }
        this.tagName = name;
    }

    public static Tag getTag(String name) throws IllegalValueException {
        assert name != null;
        if (allTags.containsKey(name)) {
            return allTags.get(name);
        }
        Tag newTag = new Tag(name);
        allTags.put(name, newTag);
        return newTag;
    }
    
    public String getTagName() {
        return tagName;
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
        if (this == EMPTY_TAG) {
            return "[No category]";
        }
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
        if (args.equals(EMPTY_TAG_VALUE)) {
            return EMPTY_TAG;
        }
        return new Tag(args);
    }

}
