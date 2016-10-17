package seedu.tasklist.model.task;

import java.util.Objects;

import seedu.tasklist.commons.util.CollectionUtil;
import seedu.tasklist.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	private static int currentID = 0;
	
    private TaskDetails taskDetails;
    private StartTime startTime;
    private EndTime endTime;
    private Priority priority;
    private int uniqueID;
    private boolean isComplete;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskDetails taskDetails, StartTime startTime, EndTime endTime, Priority priority, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(taskDetails, startTime, endTime, priority, tags);
        this.taskDetails = taskDetails;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.uniqueID = currentID++;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.isComplete = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTaskDetails(), source.getStartTime(), source.getEndTime(), source.getPriority(), source.getTags());
    }

    @Override
    public TaskDetails getTaskDetails() {
        return taskDetails;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public int getUniqueID() {
        return uniqueID;
    }
    
    public void setUniqueId(int newuniqueId){
    	uniqueID = newuniqueId;
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

    @Override
    public boolean isComplete() {
        return isComplete;
    }
    
    public void markAsComplete() {
        isComplete = true;
    }
    
    public void markAsIncomplete() {
        isComplete = false;
    }
    
    public void setTaskDetails(TaskDetails taskDetails){
        this.taskDetails = taskDetails;
    }
    
    public void setStartTime(StartTime startTime){
        this.startTime = startTime;
    }
    
    public void setEndTime(EndTime endTime){
        this.endTime = endTime;
    }
    
    public void setPriority(Priority priority){
        this.priority = priority;
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
        return Objects.hash(taskDetails, startTime, endTime, priority, uniqueID, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
}
