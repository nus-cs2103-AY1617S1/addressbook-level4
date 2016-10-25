package seedu.todo.model.tag;

//@@author A0135805H
/**
 * Represents a Tag in a task.
 *
 * Guarantees: immutable, only {@link UniqueTagCollection} can modify
 * the package private {@link Tag#rename(String)}.
 *
 * However, since alphanumeric name is not critical to {@link Tag},
 * the validation is done at {@link seedu.todo.model.TodoModel}
 */
public class Tag {

    //TODO: Make some of the methods package private.

    /* Variables */

    //Stores a unique tag name, that is alphanumeric, and contains dashes and underscores.
    private String tagName;

    /* Default Constructor */
    public Tag() {}

    /**
     * Constructs a new tag with the given tag name.
     * This class is intentional to be package private, so only {@link UniqueTagCollection} can construct new tags.
     */
    public Tag(String name) {
        this.tagName = name;
    }

    /* Methods */
    /**
     * Renames the tag with a {@code newName}.
     * This class is intentional to be package private, so only {@link UniqueTagCollection} can rename tags.
     */
    void rename(String newName) {
        this.tagName = newName;
    }

    /* Override Methods */
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

    /* Getters */
    public String getTagName() {
        return tagName;
    }
}
