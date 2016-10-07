package seedu.oneline.testutil;

import seedu.oneline.model.tag.UniqueTagList;
import seedu.oneline.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name oldName;
    private Address address;
    private Email email;
    private Phone phone;
    private UniqueTagList tags;
    
    private TaskName name;
    private TaskTime startTime;
    private TaskTime endTime;
    private TaskTime deadline;
    private TaskRecurrence recurrence;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setOldName(Name name) {
        this.oldName = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
    
    public void setName(TaskName name) {
        this.name = name;
    }
    
    public void setStartTime(TaskTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(TaskTime endTime) {
        this.endTime = endTime;
    }
    
    public void setDeadline(TaskTime deadline) {
        this.deadline = deadline;
    }

    public void setRecurrence(TaskRecurrence recurrence) {
        this.recurrence = recurrence;
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
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getOldName().fullName + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        sb.append("a/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
