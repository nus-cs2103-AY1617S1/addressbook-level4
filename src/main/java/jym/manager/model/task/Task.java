package jym.manager.model.task;

import java.time.LocalDateTime;
import java.util.List;
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
//@@author A0153440R
public class Task extends TaskManagerItem implements ReadOnlyTask {
	
	private Description descr;
	private Location loc;
	private Deadline dueDate; //doubles as start time for now.
	private Priority pri;
	//for event functionality. Bad way to do this, short on time so it will have to do.
	private Deadline endTime; 
    private UniqueTagList tags;
    
    private Status status;

    
    /**
     * Only Description and status fields must be present and not null. Other fields can be null.
     */
    public Task(Description descr, Location loc, Deadline dueDate, Priority pri, Status status) {
        assert descr != null;
        this.descr = descr;
        this.loc = loc;
        this.dueDate = dueDate;
        this.pri = pri;
        this.status = status;
    }
    
    /**
     * Constructor with a variable amount of arguments.
     * @param description Task description
     * @param objects Various arguments (location, deadline, priority, status)
     * @throws IllegalValueException
     */
	public Task(Description description, Object ... objects) throws IllegalValueException{
		assert !CollectionUtil.isAnyNull(description, objects);
		this.descr = description;
		this.loc = new Location();
		this.dueDate = new Deadline();
		this.endTime = null;
		this.status = new Status(false);
		this.pri = new Priority(0);
		for(int i = 0; i < objects.length; i++){
    		Object o = objects[i];
    		if(o instanceof String){
    			this.loc = new Location((String)o);
    		} else if(o instanceof Location){ 
    			this.loc = (Location)o;
    		} else if(o instanceof LocalDateTime){
    			this.dueDate = new Deadline((LocalDateTime)o);
    		} else if(o instanceof Deadline){ 
    			this.dueDate = (Deadline)o;
    		} else if(o instanceof List){
    			setDates((List)o);
    		} else if(o instanceof Priority){
    			this.pri = (Priority)o;
    		} else if(o instanceof Integer){
    			this.pri = new Priority((Integer)o);
    		} else if(o instanceof UniqueTagList){
    			this.tags = new UniqueTagList((UniqueTagList)o);
    		} else if(o instanceof Status){
    			this.status = new Status(((Status)o).isComplete());
    		}
    	}
	
	}
	/**
	 * Helper function to properly set dates of the Task.
	 */
	private void setDates(List x){
		if((x).size() == 1){
			if((x).get(0) instanceof LocalDateTime){
				List<LocalDateTime> l = (List<LocalDateTime>)x;
				this.dueDate = new Deadline(l.get(0));
			} else {
				this.dueDate = ((List<Deadline>) x).get(0);
			}
		} else {
			if((x).get(0) instanceof LocalDateTime){
				List<LocalDateTime> l = (List<LocalDateTime>)x;
    			this.dueDate = new Deadline(l.get(0));
    			this.endTime = new Deadline(l.get(1));
			} else {
				//must be of type deadline
				List<Deadline> l = (List<Deadline>)x;
				this.dueDate = l.get(0);
				this.endTime = l.get(1);
			}
		}
	}
	/**
	 * Constructor for exact input. Used for testing purposes. 
	 * @param d
	 * @param due
	 * @param location
	 * @param status
	 */
	public Task(Description d, Deadline due, Location location, Status status){
		this.descr = d;
		this.dueDate = due;
		this.loc = location;
		this.status = status;
		this.endTime = null;
	
	}
    /**
     * Constructor used for when all fields are available. Used for retrieving saved data.
     */
    public Task(Description description, Location location, Deadline due, Deadline end, Priority p, UniqueTagList tags, Status status) {
    	this.descr = description;
    	this.loc = (location == null)? new Location():location;
    	this.dueDate = (due == null) ? new Deadline():due;
    	this.endTime = (end == null) ? null : end;
    	this.pri = p;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
        this.status = status;
    }
    /**
     * Copy constructor.
     * @throws IllegalValueException 
     */
    public Task(ReadOnlyTask source) {
  //      this(source.getDescription(), source.getLocation(), source.getDate(), source.getEndTime(), source.getPriority(), source.getTags());
        this(source.getDescription(), source.getLocation(), source.getDate(), source.getPriority(), source.getStatus());
    }
   /**
    * When the task needs to be edited. 
    */
    public Task update(ReadOnlyTask source){
    	this.descr = source.getDescription();
    	if(source.getLocation() != null){
    		this.loc = source.getLocation();
    	}
    	if(source.getDate().hasDeadline()){
    		this.dueDate = source.getDate();
    	}
    	if(source.getEndTime() != null){
    		this.endTime = source.getEndTime();
    	}
    	
    	return this;
    }
    /**
     * Constructor for tasks with only the description (floating tasks).
     */
    public Task(Description description) {
        assert !CollectionUtil.isAnyNull(description);
        this.descr = description;
        this.loc = new Location();
        this.dueDate = new Deadline();
        this.pri = new Priority();
        this.status = new Status(false);
        this.endTime = null;
    }

    /**
     * Getters and setters. 
     */
    @Override
    public Description getDescription() {
        return this.descr;
    }

    @Override
    public Location getLocation() {
        return this.loc;
    }

	@Override
	public Deadline getDate() {
		return this.dueDate;
	}
	public Deadline getEndTime(){
		return this.endTime;
	}

	@Override
	public Priority getPriority() {
		return this.pri;
	}

    
    @Override
    public UniqueTagList getTags() {
        return new UniqueTagList(tags);
    }
    
    @Override
    public Status getStatus() {
        return this.status;
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

    public boolean hasDeadline(){
    	return this.dueDate != null && this.dueDate.hasDeadline();
    }

	public void setDescr(Description descr) {
		this.descr = descr;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
	public void setDueDate(Deadline dueDate) {
		this.dueDate = dueDate;
	}
	public void setPri(Priority pri) {
		this.pri = pri;
	}

	public boolean isEvent(){
		return this.endTime != null;
	}
	public String getDateString(){
		if(this.endTime != null){
			return this.dueDate + " -> " + this.endTime; 
		}
		return this.dueDate.toString();
	}


}
