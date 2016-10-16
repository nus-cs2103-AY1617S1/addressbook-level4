package harmony.mastermind.model.task;

import java.util.Date;

import harmony.mastermind.model.tag.UniqueTagList;

//@@author A0124797R
public interface ReadOnlyTask {
    //provide safe read, unmodifiable task object

    public String getName();
    public Date getStartDate();
    public Date getEndDate();
    public UniqueTagList getTags();
    
    public boolean isMarked();
    public boolean isFloating();
    public boolean isDeadline();
    public boolean isEvent();
    
    //@@author A0124797R
    default boolean isSameTask(ReadOnlyTask task) {
        return this.getName().equals(task.getName());
    }
    
    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Tags: ")
                .append(getTags().toString());
        return builder.toString();
    }
}
