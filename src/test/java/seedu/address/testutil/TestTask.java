package seedu.address.testutil;

import seedu.address.model.deadline.UniqueDeadlineList;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

/**
 * A mutable person object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    private Name name;
    private Startline startline;
    private UniqueDeadlineList deadlines;
    private Priority priority;
    private UniqueTagList tags;

    public TestTask() {
    	deadlines = new UniqueDeadlineList();
        tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartline(Startline startline){
    	this.startline = startline;
    }

//    public void setDeadline(Deadline deadline) {
//        this.deadline = deadline;
//    }

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
    public UniqueDeadlineList getDeadlines() {
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
        sb.append("d/" + this.deadlinesString() + " ");
        sb.append("p/" + this.getPriority().value + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
 