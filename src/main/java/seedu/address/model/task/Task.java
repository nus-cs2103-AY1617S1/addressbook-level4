package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a task in the JYM program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Description discription;
    private Phone phone;
    private Email email;
    private Location location;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Discription discription, Phone phone, Email email, Location location, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(discription, phone, email, location, tags);
        this.discription = discription;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDiscription(), source.getPhone(), source.getEmail(), source.getLocation(), source.getTags());
    }

    @Override
    public Discription getDiscription() {
        return discription;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public Email getEmail() {
        return email;
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
        return Objects.hash(discription, phone, email, location, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
