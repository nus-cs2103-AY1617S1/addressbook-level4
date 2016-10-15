package seedu.address.model.task;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;

import java.util.Objects;

/**
 * Represents a Task in the task list.
 * A Floating task is created by using the constructor with only the (Name and UniqueTagList)
 * E.g. Task floating = new Task(name, uniqueTagList)
 * A Non Floating task is created by using the constructor with (Name, UniqueTagList, TaskDate, TaskDate)
 * E.g. 
 * TaskDate startDate, endDate;
 * startDate = new TaskDate(...);
 * endDate = new TaskDate(...);
 * Task nonFloating = new Task(name, uniqueTagList, startDate, endDate); 
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private UniqueTagList tags;
    
    private TaskDate startDate, endDate;
    private TaskType taskType;
    
    private RecurringType recurringType;
    
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = tags;
        this.startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        this.endDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        taskType = TaskType.FLOATING;
        recurringType = RecurringType.NONE;
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate, RecurringType recurringType) {
        this(name, tags);
        assert !CollectionUtil.isAnyNull(name, tags);
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskType = TaskType.NON_FLOATING;
        this.recurringType = recurringType;
    }
    
    public Task(){}
    
    public boolean isValidTimeSlot(){
    	if(startDate!=null && endDate!=null){
    		return (endDate.getParsedDate()).after(startDate.getParsedDate());
    	}else{
    		return true;
    	}
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTags(), source.getStartDate(), source.getEndDate(), source.getRecurringType());
        if (source.getEndDate().getDate() == TaskDate.DATE_NOT_PRESENT) {
            taskType = TaskType.FLOATING;
        }
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
    public TaskType getTaskType() {
        return taskType;
    }
    @Override
    public RecurringType getRecurringType() {
        return recurringType;
    }
    
    public void setType(TaskType type) {
        this.taskType = type;
    }
    public void setRecurringType(RecurringType type) {
        this.recurringType = type;
    }
    
    public boolean hasOnlyDateLine() {
        if (taskType == TaskType.FLOATING) {
            return false;
        }
        if (startDate.getDate() != TaskDate.DATE_NOT_PRESENT){
            return false;
        }
        return true;
    }
    
    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
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

}
