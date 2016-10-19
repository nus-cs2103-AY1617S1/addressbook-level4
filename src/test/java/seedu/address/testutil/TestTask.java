package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private EndTime address;
    private StartTime start;
    private Date date;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndTime(EndTime address) {
        this.address = address;
    }

    public void setStartTime(StartTime start) {
        this.start = start;
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
    public StartTime getStartTime() {
        return start;
    }

    @Override
    public EndTime getEndTime() {
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

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
//        sb.append("d/" + this.getDate().value + " ");
        sb.append("from " + this.getStartTime().appearOnUIFormat() + " ");
        sb.append("to " + this.getEndTime().appearOnUIFormat() + " ");
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public String getDone() {
		return "false";
	}
}
