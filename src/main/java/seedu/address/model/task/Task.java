package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Location;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Deadline;
import java.util.Objects;

/**
 * Represents a task in the JYM program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description description;
   
    private Location location;


    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Description description, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, location, tags);
        this.description = description;
     
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getLocation(), source.getTags());
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Location getLocation() {
        return location;
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }


    @Override
    public Deadline getDeadline() {
        // TODO Auto-generated method stub
        return null;
    }


}
