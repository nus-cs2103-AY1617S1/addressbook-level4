package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

	private Name name;
    private Detail detail;
    private TaskDate fromDate;
    private TaskDate tillDate;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public void setFromDate(TaskDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setTillDate(TaskDate tillDate) {
        this.tillDate = tillDate;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Detail getDetail() {
        return this.detail;
    }

    @Override
    public TaskDate getFromDate() {
        return this.fromDate;
    }

    @Override
    public TaskDate getTillDate() {
        return this.tillDate;
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
        sb.append("p/" + this.getDetail().value + " ");
        sb.append("e/" + this.getFromDate().toString() + " ");
        sb.append("a/" + this.getTillDate().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
