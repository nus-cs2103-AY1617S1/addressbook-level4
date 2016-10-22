//@@author A0139930B
package seedu.taskitty.model.task;

import seedu.taskitty.commons.util.CollectionUtil;
import seedu.taskitty.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the taskManager.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask, Comparable<Task> {
    
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
    private TaskDate startDate;
    private TaskTime startTime;
    private TaskDate endDate;
    private TaskTime endTime;
    private int numArgs;
    private boolean isDone;

    private UniqueTagList tags;

    /**
     * Constructor for a "todo" Task.
     * "todo" is a Task only has name
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        
        this.name = name;
        this.tags = new UniqueTagList(tags);
        this.numArgs = TASK_COMPONENT_COUNT;
    }
    
    /**
     * Constructor for a "deadline" Task.
     * "deadline" is a Task only has name, endDate and endTime
     * Every field must be present and not null.
     */
    public Task(Name name, TaskDate endDate, TaskTime endTime, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, endDate, endTime, tags);
        
        this.name = name;
        this.endDate = endDate;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags);
        this.numArgs = DEADLINE_COMPONENT_COUNT;
    }
    
    /**
     * Constructor for a "event" Task.
     * "event" is a Task with all fields.
     * This constructor allows nulls and can be used when unsure which values are null
     */
    public Task(Name name, TaskDate startDate, TaskTime startTime,
            TaskDate endDate, TaskTime endTime, UniqueTagList tags) {
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        
        if (this.startDate != null && this.startTime != null) {
            numArgs = EVENT_COMPONENT_COUNT;
        } else if (this.endDate != null && this.endTime != null) {
            numArgs = DEADLINE_COMPONENT_COUNT;
        } else {
            numArgs = TASK_COMPONENT_COUNT;
        }
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getStartTime(),
                source.getEndDate(), source.getEndTime(), source.getTags());
        this.isDone = source.getIsDone();
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TaskDate getStartDate() {
        return startDate;
    }
    
    @Override
    public TaskDate getEndDate() {
        return endDate;
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
	public boolean isTodo() {
		return numArgs == 1;
	}
	
	@Override
	public boolean isDeadline() {
		return numArgs == 3;
	}
	
	@Override
	public boolean isEvent() {
		return numArgs == 5;
	}
	
	@Override
    public int compareTo(Task taskToCompare) {
	    // sort all tasks that are done to the back of the list
	    if (this.getIsDone() && !taskToCompare.getIsDone()) {
	        return 1;
	    } else if (!this.getIsDone() && taskToCompare.getIsDone()) {
	        return -1;
	    }
        if (this.getNumArgs() == taskToCompare.getNumArgs()) {
            // sort events according to their start time and end time
            if (this.isEvent()) {
                if (!this.getStartDate().equals(taskToCompare.getStartDate())) {
                    return this.getStartDate().getDate().compareTo(taskToCompare.getStartDate().getDate());
                } else if (!this.getStartTime().equals(taskToCompare.getStartTime())) {
                    return this.getStartTime().time.compareTo(taskToCompare.getStartTime().time);                    
                }
            }
            // if event has same start date and start time, sort it by its end date or end time like deadline
            if (this.isEvent() || this.isDeadline()) {
                if (!this.getEndDate().equals(taskToCompare.getEndDate())) {
                    return this.getEndDate().getDate().compareTo(taskToCompare.getEndDate().getDate());
                } else if (!this.getEndTime().equals(taskToCompare.getEndTime())) {
                    return this.getEndTime().time.compareTo(taskToCompare.getEndTime().time);                    
                } 
            }
            // if event and deadline has all the same dates and times, sort by the name of the task like todo
            return this.getAsText().compareTo(taskToCompare.getAsText());
        } else {
            return this.getNumArgs() - taskToCompare.getNumArgs();
        } 
    }
	
}
