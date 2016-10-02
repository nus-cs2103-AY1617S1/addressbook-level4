package seedu.address.model.tag;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Category in the to-do list.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Category {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Category names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public String category;

    public Category() {
    }

    /**
     * Validates given Category name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Category(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTagName(name)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.category = name;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Category // instanceof handles nulls
                && this.category.equals(((Category) other).category)); // state check
    }

    @Override
    public int hashCode() {
        return category.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + category + ']';
    }

}
