package seedu.address.model.person;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Status.State;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a DatedTask in the to-do-list.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Description description;
    private Date date;
    private Time time;
    private Status status;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Description phone, Date email, Time address, Status status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, status, tags);
        this.name = name;
        this.description = phone;
        this.date = email;
        this.time = address;
        this.status = status;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDescription(), source.getDate(), source.getTime(), source.getStatus(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

    @Override
    public Status getStatus() {
        return status;
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

    public void setName(Name name) {
        this.name = name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
    
    public void setAsDone(){
        this.setStatus(new Status(State.DONE));
    }
    
    public void setAsOverdue(){
        this.setStatus(new Status(State.OVERDUE));
    }
    
    public void setAsNorm(){
        this.setStatus(new Status(State.NONE));
    }
    
    private void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, date, time, status, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
