package seedu.taskitty.model.task;

import seedu.taskitty.commons.exceptions.IllegalValueException;
import seedu.taskitty.commons.util.CollectionUtil;
import seedu.taskitty.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {
    
    public static final int TASK_COMPONENT_INDEX_NAME = 0;
    public static final int TASK_COMPONENT_COUNT = 1;
    
    public static final int DEADLINE_COMPONENT_INDEX_NAME = 0;
    public static final int DEADLINE_COMPONENT_INDEX_DATE = 1;
    public static final int DEADLINE_COMPONENT_INDEX_END_TIME = 2;
    public static final int DEADLINE_COMPONENT_COUNT = 3;
    
    public static final int EVENT_COMPONENT_INDEX_NAME = 0;
    public static final int EVENT_COMPONENT_INDEX_DATE = 1;
    public static final int EVENT_COMPONENT_INDEX_START_TIME = 2;
    public static final int EVENT_COMPONENT_INDEX_END_TIME = 3;
    public static final int EVENT_COMPONENT_COUNT = 4;

    private Name name;
    private boolean isDone;
    private TaskDate date;
    private TaskTime startTime;
    private TaskTime endTime;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, TaskDate date, TaskTime startTime, TaskTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getDate(), source.getStartTime(),
                source.getEndTime(), source.getTags());
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskDate getDate() {
        return date;
    }

    @Override
    public TaskTime getStartTime() {
        return startTime;
    }

    @Override
    public TaskTime getEndTime() {
        return endTime;
    }

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
    		try {
    			this.name = new Name(this.name + " DONE");
    		} catch (IllegalValueException e) {
    			e.printStackTrace();
    		}
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
}
