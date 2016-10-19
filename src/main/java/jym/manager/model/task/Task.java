package jym.manager.model.task;

import java.time.LocalDateTime;
import java.util.Objects;

import jym.manager.commons.exceptions.IllegalValueException;
import jym.manager.commons.util.CollectionUtil;
import jym.manager.model.tag.UniqueTagList;
import jym.manager.model.task.Deadline;
import jym.manager.model.task.Description;
import jym.manager.model.task.Location;
import jym.manager.model.task.Priority;

/**
 * Represents a task in the JYM program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

	private Description descr;
	private Location loc;
	private LocalDateTime dueDate;
	private Priority pri;
	private Complete compl;

    private UniqueTagList tags;

    
	public Task(Description description, Object ... objects) throws IllegalValueException{
		assert !CollectionUtil.isAnyNull(description, objects);
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
    		} else if(o instanceof UniqueTagList){
    			this.tags = new UniqueTagList((UniqueTagList)o);
    		}
    	}
	}

    /**
     * Every field must be present and not null.
     */

    public Task(Description description, Location location, LocalDateTime due, Priority p, UniqueTagList tags) {
    	assert !CollectionUtil.isAnyNull(description, location, due);
    	this.descr = description;
    	this.loc = location;
    	this.dueDate = due;
    	this.pri = p;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getDescription(), source.getLocation(), source.getDate(), source.getPriority(), source.getTags());
    }
   

    public Task(Description description, UniqueTagList tags) {
        assert !CollectionUtil.isAnyNull(description, tags);
        this.descr = description;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.loc = null;
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
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(this.descr, this.loc, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public Complete getComplete() {
		return this.compl;
	}




}
