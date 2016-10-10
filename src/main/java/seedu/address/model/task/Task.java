package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Location;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Deadline;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDateTime;
/**
 * Represents a task in the JYM program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task extends TaskManagerItem implements ReadOnlyTask {

	private Description descr;
	private Location loc;
	private LocalDateTime dueDate;
	private Priority pri;
 //   private UniqueTagList tags;

	public Task(Description description, Object ... objects) throws IllegalValueException{
		this.descr = description;
		this.loc = null;
		this.dueDate = null;
		this.pri = new Priority(0);
		for(int i = 0; i < objects.length; i++){
    		Object o = objects[i];
    		if(o instanceof String){
    			this.loc = new Location((String)o);
    		} else if(o instanceof LocalDateTime){
    			this.dueDate = (LocalDateTime)o;
    		} else if(o instanceof Integer){
    			this.pri = new Priority((Integer)o);
    		}
    	}
		
		
	}
	
    public Task(Description description, Location location, LocalDateTime due, Priority p) {
    	assert !CollectionUtil.isAnyNull(description, location, due);
    	this.descr = description;
    	this.loc = location;
    	this.dueDate = due;
    	this.pri = p;
  //      this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getLocation(), source.getDate(), source.getPriority());
    }
   
//    @Override
//    public UniqueTagList getTags() {
//        return new UniqueTagList(tags);
//    }

    /**
     * Replaces this person's tags with the tags in the argument tag list.
     */
//    public void setTags(UniqueTagList replacement) {
//        tags.setTags(replacement);
//    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.descr, this.loc, this.dueDate);
    }

    @Override
    public String toString() {
        return getAsText();
    }
    
    @Override
    public Description getDescription() {
        return this.descr;
    }

    @Override
    public Location getLocation() {
        return this.loc;
    }

	@Override
	public LocalDateTime getDate() {
		return this.dueDate;
	}

	@Override
	public Priority getPriority() {
		return this.pri;
	}


}
