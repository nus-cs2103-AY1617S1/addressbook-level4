package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.Location;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;

import java.time.LocalDateTime;

import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Description description;
    private Location address;
    private LocalDateTime deadline;
//    private Phone phone;
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public void setDate(LocalDateTime date) {
        this.deadline = date;
    }
//
//    public void setPhone(Phone phone) {
//        this.phone = phone;
//    }

    @Override
    public Description getDescription() {
        return description;
    }

//    @Override
//    public Phone getPhone() {
//        return phone;
//    }
//
    @Override
    public LocalDateTime getDate() {
        return this.deadline;
    }

    @Override
    public Location getLocation() {
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
        sb.append("add " + this.getDescription().toString() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }


	@Override
	public Priority getPriority() {
		// TODO Auto-generated method stub
		return null;
	}
}
