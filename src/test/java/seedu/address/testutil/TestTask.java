package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
<<<<<<< HEAD:src/test/java/seedu/address/testutil/TestTask.java
    private Deadline time;
=======
    private Date date;
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439:src/test/java/seedu/address/testutil/TestTask.java
    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

<<<<<<< HEAD:src/test/java/seedu/address/testutil/TestTask.java
    public void setDeadline(Deadline time) {
        this.time = time;
=======
    public void setAddress(Date date) {
        this.date = date;
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439:src/test/java/seedu/address/testutil/TestTask.java
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
<<<<<<< HEAD:src/test/java/seedu/address/testutil/TestTask.java
    public Deadline getDeadline() {
        return time;
=======
    public Date getDate() {
        return date;
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439:src/test/java/seedu/address/testutil/TestTask.java
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
<<<<<<< HEAD:src/test/java/seedu/address/testutil/TestTask.java
        sb.append("d/" + this.getDeadline().time + " ");
=======
        if (date instanceof EventDate) {
            EventDate eventDate = (EventDate) this.getDate();
            sb.append("s/" + eventDate.getStartDate() + " ");
            sb.append("e/" + eventDate.getEndDate() + " ");
        } else {
            assert date instanceof Deadline;
            sb.append("d/" + this.getDate().getValue() + " ");
        }
>>>>>>> e56ae7b38b2e0a1b1133fb40d95b25c096221439:src/test/java/seedu/address/testutil/TestTask.java
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
