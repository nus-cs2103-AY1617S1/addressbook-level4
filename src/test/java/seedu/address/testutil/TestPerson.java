package seedu.address.testutil;

import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyTask {

    private Name name;
    private EndTime address;
    private StartTime email;
    private Date1 phone;
    private boolean isCompleted;
    private UniqueTagList tags;
    private String isComplete;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndTime(EndTime address) {
        this.address = address;
    }

    public void setStartTime(StartTime email) {
        this.email = email;
    }

    public void setDate(Date1 phone) {
        this.phone = phone;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Date1 getStart() {
        return phone;
    }

    @Override
    public StartTime getEnd() {
        return email;
    }

    @Override
    public EndTime getAddress() {
        return address;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }
    //@@author A0146749N
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("d/" + this.getStart().value + " ");
        sb.append("s/" + this.getEnd().value + " ");
        sb.append("e/" + this.getAddress().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public void markAsComplete() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public String getCompletion() {
	return isComplete;
    }

    @Override
    public void setCompletion(String completion) {
        this.isComplete = completion;
    }
}
