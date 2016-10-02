package seedu.address.model.todo;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a ToDo in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class ToDo implements ReadOnlyToDo {

    private Title title;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public ToDo(Title title) {
        assert !CollectionUtil.isAnyNull(title);
        this.title = title;
//        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public ToDo(ReadOnlyToDo source) {
        this(source.getTitle());
    }

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this ToDo's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyToDo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyToDo) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
