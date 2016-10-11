package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
//    private Address address;
    private Time time;
    private Date date;
//    private UniqueTagList tags;

    public TestTask() {
//        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

//    public void setAddress(Address address) {
//        this.address = address;
//    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

//    @Override
//    public Address getAddress() {
//        return address;
//    }

//    @Override
//    public UniqueTagList getTags() {
//        return tags;
//    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("p/" + this.getDate().value + " ");
        sb.append("e/" + this.getTime().value + " ");
//        sb.append("a/" + this.getAddress().value + " ");
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
