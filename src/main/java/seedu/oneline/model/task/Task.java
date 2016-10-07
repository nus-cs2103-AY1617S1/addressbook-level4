package seedu.oneline.model.task;

import java.util.Objects;

import seedu.oneline.commons.util.CollectionUtil;
import seedu.oneline.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name oldName;
    private Phone phone;
    private Email email;
    private Address address;

    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Phone phone, Email email, Address address, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, phone, email, address, tags);
        this.oldName = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    public Task(TaskName name, TaskTime startTime, TaskTime endTime, TaskTime deadline, TaskRecurrence recurrence, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime, deadline, recurrence, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.deadline = deadline;
        this.recurrence = recurrence;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getOldName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getTags());
    }

    @Override
    public Name getOldName() {
        return oldName;
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
    public Address getAddress() {
        return address;
    }

    @Override
    public TaskName getName() {
        return name;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

    @Override
    public TaskTime getDeadline() {
        return deadline;
    }

    @Override
    public TaskRecurrence getRecurrence() {
        return recurrence;
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
        return Objects.hash(oldName, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
