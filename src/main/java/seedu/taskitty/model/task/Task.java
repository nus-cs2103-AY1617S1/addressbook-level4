//@@author A0139930B
package seedu.taskitty.model.task;

import seedu.taskitty.commons.util.CollectionUtil;
import seedu.taskitty.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the taskManager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    
    public static final int TASK_COMPONENT_INDEX_NAME = 0;
    public static final int TASK_COMPONENT_COUNT = 1;
    
    public static final int DEADLINE_COMPONENT_INDEX_NAME = 0;
    public static final int DEADLINE_COMPONENT_INDEX_END_DATE = 1;
    public static final int DEADLINE_COMPONENT_INDEX_END_TIME = 2;
    public static final int DEADLINE_COMPONENT_COUNT = 3;
    
    public static final int EVENT_COMPONENT_INDEX_NAME = 0;
    public static final int EVENT_COMPONENT_INDEX_START_DATE = 1;
    public static final int EVENT_COMPONENT_INDEX_START_TIME = 2;
    public static final int EVENT_COMPONENT_INDEX_END_DATE = 3;
    public static final int EVENT_COMPONENT_INDEX_END_TIME = 4;
    public static final int EVENT_COMPONENT_COUNT = 5;

    private Name name;
    private TaskPeriod period;
    private int numArgs;
    private boolean isDone;

    private UniqueTagList tags;

    /**
     * Constructor for a "todo" Task.
     * "todo" is a Task only has name
     * Every field must be present and not null.
     */
    public Task(Name name, TaskPeriod period, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        
        this.name = name;
        this.period = period;
        this.tags = new UniqueTagList(tags);
        this.numArgs = TASK_COMPONENT_COUNT;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPeriod(), source.getTags());
        this.isDone = source.getIsDone();
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public int getNumArgs() {
        return numArgs;
    }

    //@@author
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }
    
    /** 
     * Marks task as done.
     */
    public void markAsDone() {
    	if (!isDone) {
    		this.isDone = true;
    	}
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public boolean getIsDone() {
		return isDone;
	}
	
	@Override
	public TaskPeriod getPeriod() {
	    return period;
	}
	
	@Override
	public boolean isTodo() {
		return period.isTodo();
	}
	
	@Override
	public boolean isDeadline() {
		return period.isDeadline();
	}
	
	@Override
	public boolean isEvent() {
		return period.isEvent();
	}
}
