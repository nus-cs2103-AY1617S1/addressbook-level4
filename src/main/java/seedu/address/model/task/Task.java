package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date startDate;
    private Date endDate;
    private Location address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date startDate, Date endDate, Location address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startDate, endDate, address, tags);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getLocation(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public Location getLocation() {
        return address;
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLocation(Location address) {
        this.address = address;
    }
    

    /**
     * Add completed tag to indicate task done.
     */
    public void markComplete() throws DuplicateTagException {
        try {
            this.tags.add(new Tag("Completed"));
        } catch (IllegalValueException ive) {
            assert false : "The tag cannot be illegal value";
        }
    }
    
    
    public void copyField(Task task) {
        setName(task.getName());
        setStartDate(task.getStartDate());
        setEndDate(task.getEndDate());
        setLocation(task.getLocation());
        setTags(task.getTags());
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startDate, endDate, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
