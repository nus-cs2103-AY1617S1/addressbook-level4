package seedu.address.testutil;

import seedu.address.model.deadline.Deadline;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Startline startline;
    private Deadline deadlines;
    private Priority priority;
    private UniqueTagList tags;
    private Repeating repeating;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartline(Startline startline){
    	this.startline = startline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadlines = deadline;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public Startline getStartline(){
    	return startline;
    }

    @Override
    public Deadline getDeadline() {
        return deadlines;
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
        sb.append("s/" + this.getStartline().value + " ");
        sb.append("d/" + this.getDeadline().value + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

	@Override
	public Repeating getRepeating() {
		return repeating;
	}

	public void toggleRepeat() {
		repeating.setRepeating(!repeating.getRepeating());;		
	}
}
 