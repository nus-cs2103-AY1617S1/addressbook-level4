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
    /* Variables */
    //Stores a unique tag name, that is alphanumeric, and contains dashes and underscores.
    private String tagName;

    /**
     * Constructs a new tag with the given tag name.
     * This class is intentional to be package private, so only {@link UniqueTagCollection} can construct new tags.
     */
    public Tag(String name) {
        this.tagName = name;
    }

    /* Methods */
    //@@author A0135805H-unused
    //Feature to be implemented in V0.5
    /**
     * Renames the tag with a {@code newName}.
     * This class is intentional to be package private, so only {@link UniqueTagCollection} can rename tags.
     */
    void rename(String newName) {
        this.tagName = newName;
    }

    //@@author A0135805H-reused
    /* Override Methods */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag && this.tagName.equals(((Tag) other).tagName)) // if is tag
                || (other instanceof String && this.tagName.equals(other)); // if is string
                //Enables string comparison for hashing.
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
