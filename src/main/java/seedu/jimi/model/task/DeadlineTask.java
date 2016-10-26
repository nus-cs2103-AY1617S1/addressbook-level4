package seedu.jimi.model.task;

import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.UniqueTagList;

/**
 * Represents a task with a deadline which inherits from FloatingTask
 * @@author A0148040R
 *
 */
public class DeadlineTask extends FloatingTask implements ReadOnlyTask {

    private DateTime deadline;
    
    public DeadlineTask(Name name, DateTime deadline, UniqueTagList tags, boolean isCompleted, Priority priority) {
        super(name, tags, isCompleted, priority);
        assert deadline != null;
        this.deadline = deadline;
    }
    
    public DeadlineTask(Name name, DateTime deadline, UniqueTagList tags, Priority priority) {
        super(name, tags, priority);
        assert deadline != null;
        this.deadline = deadline;
    }
    
    /**
     * Copy constructor.
     */
    public DeadlineTask(DeadlineTask source) {
        super(source.getName(), source.getTags(), source.isCompleted(), source.getPriority());
        this.deadline = ((DeadlineTask) source).getDeadline();
    }
    
    
    public DateTime getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other instanceof DeadlineTask // instanceof handles nulls
                && super.isSameStateAs(other)
                && ((DeadlineTask)other).getDeadline().equals(this.getDeadline())
                );
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" ")
               .append(getDeadline())
               .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Priority: ")
        .append(getPriority());
        return builder.toString();
    }
    

}
