package seedu.malitio.model.task;

import java.util.Objects;

import seedu.malitio.commons.util.CollectionUtil;
import seedu.malitio.model.tag.UniqueTagList;

public class Schedule implements ReadOnlySchedule {

    private Name name;
    private UniqueTagList tags;
    
    public Schedule(ReadOnlySchedule source) {
        this(source.getName(), source.getTags());
    }
    
    public Schedule(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public DateTime getDue() {
        return null;
    }

    @Override
    public DateTime getStart() {
        return null;
    }

    @Override
    public DateTime getEnd() {
        return null;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlySchedule // instanceof handles nulls
                && this.isSameStateAs((ReadOnlySchedule) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
