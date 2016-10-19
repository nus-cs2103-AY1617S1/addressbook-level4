package seedu.task.testutil;

import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
//    private Address address;
//    private Email email;
//    private Phone phone;
    private UniqueTagList tags;
    private boolean isCompleted;

    public TestTask() {
        tags = new UniqueTagList();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public void setEmail(Email email) {
//        this.email = email;
//    }
//
//    public void setPhone(Phone phone) {
//        this.phone = phone;
//    }

    @Override
    public Name getName() {
        return name;
    }

//    @Override
//    public Phone getPhone() {
//        return phone;
//    }
//
//    @Override
//    public Email getEmail() {
//        return email;
//    }
//
//    @Override
//    public Address getAddress() {
//        return address;
//    }
    @Override
    public boolean getComplete() {
        return isCompleted;
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
        sb.append("add " + this.getName().taskName + " ");
//        sb.append("p/" + this.getPhone().value + " ");
//        sb.append("e/" + this.getEmail().value + " ");
//        sb.append("a/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
    
    public String getArgs() {
        StringBuilder sb = new StringBuilder();
        sb.append(" "+this.getName().taskName + " ");
//        sb.append("p/" + this.getPhone().value + " ");
//        sb.append("e/" + this.getEmail().value + " ");
//        sb.append("a/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
