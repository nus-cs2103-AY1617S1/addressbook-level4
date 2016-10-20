package seedu.gtd.testutil;

import seedu.gtd.model.task.*;
import seedu.gtd.model.tag.UniqueTagList;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private DueDate dueDate;
    private Address address;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public DueDate getDueDate() {
        return dueDate;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public Priority getPriority() {
        return priority;
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
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getDueDate().value + " ");
        sb.append("a/" + this.getAddress().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
