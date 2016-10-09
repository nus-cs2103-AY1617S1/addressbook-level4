package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Floating Task in the Task Manager. Guarantees: details are
 * present and not null, field values are validated.
 */
public class FloatingTask implements Entry {

    private Title title;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public FloatingTask(Title title, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(title, tags);
        this.title = title;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public FloatingTask(Entry source) {
        this(source.getTitle(), source.getTags());
    }

    @Override
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title newTitle) {
        this.title = newTitle;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Entry // instanceof handles nulls
                && this.isSameStateAs((Entry) other));
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
    
    @Override
    public boolean isSameStateAs(Entry other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTitle().equals(this.getTitle())); 
    }

    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
