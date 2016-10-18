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
    private TaskType type;
    
    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, tags);
        this.name = name;
        this.tags = tags;
        this.startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        this.endDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
        type = TaskType.FLOATING;
    }

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate) {
        this(name, tags);
        assert !CollectionUtil.isAnyNull(name, tags);
        this.startDate = startDate;
        this.endDate = endDate;
        type = TaskType.NON_FLOATING;
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
        this(source.getName(), source.getTags(), source.getStartDate(), source.getEndDate());
        
        if (source.getEndDate().getDate() == TaskDate.DATE_NOT_PRESENT) {
            type = TaskType.FLOATING;
        }
        
        if(source.getType() == TaskType.COMPLETED) type = TaskType.COMPLETED;
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
    public TaskType getType() {
        return type;
    }
    @Override
    public void setType(TaskType type) {
        this.type = type;
    }
    
    public boolean hasOnlyDateLine() {
        if (type == TaskType.FLOATING) {
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

	@Override
	public void updateTask(Name name, UniqueTagList tags, TaskDate startDate, TaskDate endDate) {
		if(name != null)
			this.name = name;
		
		if(tags != null){
			
			this.tags = tags;
			
		}
		
		if(this.startDate.equals(new TaskDate(TaskDate.DATE_NOT_PRESENT))
				&& this.endDate.equals(new TaskDate(TaskDate.DATE_NOT_PRESENT))
				&& endDate != null) {
			this.type = TaskType.NON_FLOATING;
		}
		
		if(startDate != null) {
			this.startDate = startDate;
		} else if(endDate != null) {
			this.startDate = new TaskDate(TaskDate.DATE_NOT_PRESENT);
		}
		
		if(endDate != null)
			this.endDate = endDate;
	}

}
