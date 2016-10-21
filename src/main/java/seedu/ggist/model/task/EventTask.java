package seedu.ggist.model.task;

import java.util.Objects;

import seedu.ggist.commons.util.CollectionUtil;
import seedu.ggist.model.tag.UniqueTagList;

/**
 * Represents a EventTask in the task manager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventTask extends Task implements ReadOnlyTask {
    
    /**
     * Every field must be present and not null.
     */
    public EventTask(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority) {
        super(taskName, startDate, startTime, endDate, endTime, priority);
    }
    
    public EventTask(TaskName taskName, TaskDate startDate, TaskTime startTime, TaskDate endDate, TaskTime endTime, Priority priority, boolean done) {
        super(taskName, startDate, startTime, endDate, endTime, priority);
        this.done = done;
    }
   
    /**
     * Copy constructor.
     */
    public EventTask(ReadOnlyTask source) {
        this(source.getTaskName(), source.getStartDate(), source.getStartTime(), source.getEndDate(), source.getEndTime(), source.getPriority(), source.getDone());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, startDate, startTime, endDate, endTime, priority);
    }
}
