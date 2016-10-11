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
    public EventTask(TaskName taskName, TaskDate taskDate, TaskTime startTime, TaskTime endTime, UniqueTagList tags) {
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public EventTask(ReadOnlyTask source) {
        this(source.getTaskName(), source.getDate(), source.getStartTime(), source.getEndTime(), source.getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, taskDate, startTime, endTime, tags);
    }
}
