package seedu.jimi.testutil;

import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.event.Event;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * @@author A0143471L
 * 
 * A mutable event object. For testing only.
 *
 */
public class TestEvent implements ReadOnlyTask {

    private Name name;
    private DateTime start;
    private DateTime end;
    private UniqueTagList tags;
    private boolean isCompleted;
    private Priority priority;
  
    public TestEvent() {
        tags = new UniqueTagList();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }
    
    public DateTime getStart() {
        return start;
    }
    
    public void setEnd(DateTime end) {
        this.end = end;
    }
    
    public DateTime getEnd() {
        return end;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public boolean isCompleted() {
        return false;
    }
    
    @Override
    public Priority getPriority()  {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + "\"" + this.getName().fullName + "\"" + " ");
        sb.append("on " + this.start + " to " + this.end);
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        sb.append("p/" + this.getPriority().tagName + " ");
        return sb.toString();
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" Start :")
               .append(getStart())
               .append(" End: ")
               .append(getEnd())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Priority: ")
        .append(getPriority());
        return builder.toString();
    }
    
    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                    && (other).getName().equals(this.getName()) // state checks here onwards
                    && (other).isCompleted() == this.isCompleted()
                    && ((Event)other).getStart().equals(this.getStart())
                    && ((Event)other).getEnd().equals(this.getEnd())
                );
    }
}