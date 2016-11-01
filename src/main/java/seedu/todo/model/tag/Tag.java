package seedu.todo.model.tag;


import seedu.todo.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    private String tagName;
    private int tasksCount;
    
    public Tag() {
    }

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        assert name != null;
        String tempName = name.trim();
        if (!isValidTagName(tempName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = tempName;
        this.tasksCount = 0;
    }
    
    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name, int tasksCount) throws IllegalValueException {
        assert name != null;
        String tempName = name.trim();
        if (!isValidTagName(tempName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = tempName;
        this.tasksCount = tasksCount;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    //@@author A0142421X
    public String getName() {
        return tagName;
    }
    
    public int getCount() {
        return tasksCount;
    }
    
    public void increaseCount() {
        this.tasksCount++;
    }
    
    public void setCount(int count) {
        this.tasksCount = count;
    }
    
    public void decreaseCount() {
        if(tasksCount > 0) {
            this.tasksCount--;
        }
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
    
    
    
    

}
