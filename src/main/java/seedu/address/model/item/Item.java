package seedu.address.model.item;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Item in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Item implements ReadOnlyToDo {

    private Type type;
    private Name name;
    private TodoDate startDate;
    private TodoDate endDate;
    private TodoTime startTime;
    private TodoTime endTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Item(Type type, Name name, TodoDate sd, TodoTime st, TodoDate ed, TodoTime et, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(type, name, tags);
        this.type = type;
        this.name = name;
        this.endDate = ed;
        this.endTime = et;
        this.startDate = sd;
        this.startTime = st;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Item(ReadOnlyToDo source) {
        this(source.getType(), source.getName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getTags());
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TodoDate getStartDate() {
        return startDate;
    }
    
    @Override
    public TodoTime getStartTime() {
        return startTime;
    }

    @Override
    public TodoDate getEndDate() {
        return endDate;
    }
    
    @Override
    public TodoTime getEndTime() {
        return endTime;
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
                || (other instanceof ReadOnlyToDo // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyToDo) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
