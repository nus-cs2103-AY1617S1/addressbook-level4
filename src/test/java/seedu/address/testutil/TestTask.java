package seedu.address.testutil;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Time end;
    private Time start;
    private Done done;
    private Recurrence recurrence;
    private UniqueTagList tags;
    private Recurrence rec;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndTime(Time end) {
        this.end = end;
    }

    public void setStartTime(Time start) {
        this.start = start;
    }

    public void setDone(Done done) {
        this.done = done;
    }
    
    public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Done getDone() {
        return done;
    }

    @Override
    public Time getStartTime() {
        return start;
    }

    @Override
    public Time getEndTime() {
        return end;
    }
    
    @Override
    public Recurrence getRecurrence() {
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
        sb.append("add " + this.getName().fullName + " ");
//        sb.append("d/" + this.getDate().value + " ");
        sb.append("from " + this.getStartTime().appearOnUIFormat() + " ");
        sb.append("to " + this.getEndTime().appearOnUIFormat() + " ");
//        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
