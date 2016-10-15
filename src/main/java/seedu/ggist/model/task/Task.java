package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;


/**
 * Represents a Task in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask{

    protected TaskName taskName;
    protected TaskDate startDate;
    protected TaskTime startTime;
    protected TaskDate endDate;
    protected TaskTime endTime;
    protected UniqueTagList tags;
    protected boolean done;

    /**
     * Every field must be present and not null.
     * 
    */     
    
    public Task(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, UniqueTagList tags) {
        this.taskName = taskName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.done = false;
    }
    

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getTags());
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    public void setDone() {
        done = true;
    }
    
    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instance of handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }
 
    @Override
    public TaskName getTaskName() {
        return taskName;
    }

    @Override
    public TaskDate getStartDate() {
        return startDate;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName,startDate, startTime, endDate, endTime, tags);
    }

}
