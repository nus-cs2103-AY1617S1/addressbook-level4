package seedu.todo.model.tag;

import java.util.Collection;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * Represents a Tag in a task.
 *
 * Guarantees: immutable. To rename, a new Tag object must be created. (Renaming method is unsupported)
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
     *
     * This class is intentional to be package private, so only {@link UniqueTagCollection}
     * can construct new tags.
     */
    public Tag(String name) {
        this.tagName = name;
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

    /* Public Helper Methods */
    /**
     * Converts tags to a collection of tag names
     */
    public static Collection<String> getTagNames(Collection<Tag> tags) {
        return tags.stream().map(Tag::getTagName).collect(Collectors.toList());
    }
}
