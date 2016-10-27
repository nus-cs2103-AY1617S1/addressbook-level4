package seedu.taskscheduler.model.task;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.commons.util.CollectionUtil;
import seedu.taskscheduler.model.tag.UniqueTagList;

import java.util.Objects;

//@@author A0148145E
/**
 * Represents a Task in the task scheduler.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private TaskDateTime startDateTime;
    private TaskDateTime endDateTime;
    private Location address;
    private boolean completeStatus;
    private TaskType type;
    
    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, TaskDateTime startDateTime, TaskDateTime endDateTime, Location address, TaskType type, UniqueTagList tags) {
        this(name, new TaskDateTime(startDateTime), new TaskDateTime(endDateTime), address, type, false, tags);
    }
    
    public Task(Name name, TaskDateTime startDateTime, TaskDateTime endDateTime, Location address, TaskType type, Boolean status, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(name, startDateTime, endDateTime, address, type, status, tags);
        this.name = name;
        this.startDateTime = new TaskDateTime(startDateTime);
        this.endDateTime = new TaskDateTime(endDateTime);
        this.address = address;
        this.tags = tags;
        this.type = type;
        this.completeStatus = status;
        this.completeStatus = false;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartDate(), source.getEndDate(), source.getLocation(), source.getType(), source.getTags());
        this.completeStatus = source.getCompleteStatus();
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public TaskDateTime getStartDate() {
        return startDateTime;
    }

    @Override
    public TaskDateTime getEndDate() {
        return endDateTime;
    }

    @Override
    public Location getLocation() {
        return address;
    }
    
    @Override
    public boolean getCompleteStatus() {
        return completeStatus;
    }
    
    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(TaskDateTime startDate) {
        this.startDateTime = startDate;
    }
    
    public void setEndDate(TaskDateTime endDate) {
        this.endDateTime = endDate;
    }

    public void setLocation(Location address) {
        this.address = address;
    }
    
    public void addDuration(long duration) {
        if (startDateTime.getDate() != null)
            this.startDateTime.setDate(startDateTime.getDate().getTime() + duration + 1);
        if (endDateTime.getDate() != null)
            this.endDateTime.setDate(endDateTime.getDate().getTime() + duration + 1);
    }
    
    /**
     * Add completed tag to indicate task done.
     * @throws IllegalValueException 
     */
    public void markComplete() throws IllegalValueException {
        if (completeStatus) {
            throw new IllegalValueException("");
        } else {
            completeStatus = true;
        }
        
//        try {
//            this.tags.add(new Tag("Completed"));
//        } catch (DuplicateTagException dte) { 
//            throw dte;
//        } catch (IllegalValueException ive) {
//            assert false : "The tag cannot be illegal value";
//        } 
    }

    /**
     * Add completed tag to indicate task done.
     * @throws IllegalValueException 
     */
    public void unMarkComplete() throws IllegalValueException {
        if (!completeStatus) {
            throw new IllegalValueException("");
        } else {
            completeStatus = false;
        }
//        try {
//            this.tags.remove(new Tag("Completed"));
//        }
//        catch (NullPointerException npe) { 
//            throw npe;
//        } catch (IllegalValueException ive) {
//            assert false : "The tag cannot be illegal value";
//        }
    }

    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
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
        return Objects.hash(name, startDateTime, endDateTime, address);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    //@@author A0148145E 
    @Override
    public Task copy() {
        return new Task(this);
    }

    @Override
    public TaskType getType() {
        return type;
    }
    
    public void setType(TaskType type) {
        this.type = type;
    }

}
