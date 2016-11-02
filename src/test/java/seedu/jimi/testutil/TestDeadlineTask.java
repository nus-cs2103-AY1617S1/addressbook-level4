package seedu.jimi.testutil;

import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;
import seedu.jimi.model.task.DeadlineTask;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.model.task.Name;
import seedu.jimi.model.task.ReadOnlyTask;

/**
 * @@author A0143471L
 * 
 * A mutable deadline task object inherited from FloatingTask object. For testing only.
 *
 */
public class TestDeadlineTask extends TestFloatingTask implements ReadOnlyTask{

    private Name name;
    private UniqueTagList tags;
    private boolean isCompleted;
    private Priority priority;
    private DateTime deadline;
    
    public TestDeadlineTask() {
        tags = new UniqueTagList();
    }
    
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public UniqueTagList getTags() {
        return tags;
    }
    
    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }
    
    @Override
    public Priority getPriority()  {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
    
    @Override
    public boolean isCompleted() {
        return false;
    }
    
    public DateTime getDeadline() {
        return deadline;
    }
    
    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + "\"" + this.getName().fullName + "\"" + " ");
        sb.append("due " + this.getDeadline().getDate() + " " + this.getDeadline().getTime() + " ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        sb.append("p/" + this.getPriority().tagName + " ");
        return sb.toString();
    }
    
    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" ")
               .append(getDeadline().getDate())
               .append(" ")
               .append(getDeadline().getTime())
               .append(" ")
               .append("Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Priority: ")
        .append(getPriority());
        return builder.toString();
    }
    
    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                && super.isSameStateAs(other)
                && ((DeadlineTask)other).getDeadline().equals(this.getDeadline())
                );
    }
}
