package seedu.dailyplanner.model.task;

import java.util.Objects;

import seedu.dailyplanner.commons.util.CollectionUtil;
import seedu.dailyplanner.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Date phone;
    private StartTime email;
    private EndTime address;
    private boolean isComplete;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Date phone, StartTime email, EndTime address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isComplete = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    @Override
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public void setDate(Date date) {
        this.phone = date;
    }

    @Override
    public void setStartTime(StartTime time) {
        this.email = time;
    }

    @Override
    public void setEndTime(EndTime time) {
        this.address = time;
    }
    
    public void markAsComplete() {
	this.isComplete = true;
    }
    
    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getPhone() {
        return phone;
    }

    @Override
    public StartTime getEmail() {
        return email;
    }

    @Override
    public EndTime getAddress() {
        return address;
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
        return Objects.hash(name, phone, email, address, tags, isComplete);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    public String getCompletion() {
	return (isComplete) ? "COMPLETE" : "NOT COMPLETE"; 
    }

}
