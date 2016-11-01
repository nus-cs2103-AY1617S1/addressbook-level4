package w15c2.tusk.model.task;

import java.util.Date;

import w15c2.tusk.model.Copiable;

/*
 * Represents a highly general Task object to be subclassed
 */
public abstract class Task implements PinnableTask, CompletableTask, Comparable<Task>, Copiable<Task> {
	//@@author A0138978E
	/*
	 * All tasks are required to minimally have a description
	 */
	protected Description description;
		
	/*
	 * Indicates if this task is pind
	 */
	protected boolean pin = false;
	
	//@@author 
	/*
	 * Indicates if this task is completed
	 */
	protected boolean complete = false;
	
	//@@author A0138978E
	@Override
	public void setAsPin() {
		this.pin = true;
	}
	
	@Override
	public void setAsNotPin() {
		this.pin = false;
	}
	
	@Override
	public boolean isPinned() {
		return this.pin;
	}
	
	//@@author 
	@Override
	public void setAsComplete() {
		this.complete = true;
	}
	
	@Override
	public void setAsUncomplete() {
		this.complete = false;
	}
	
	@Override
	public boolean isCompleted(){
		return this.complete;
	}
	
	
	//@@author A0138978E
	public Task() {
		this(new Description());
	}
	
	public Task(Description description) {
		this.description = description;
	}

	public Description getDescription() {
		return description;
	}	
	
	public boolean isOverdue() {
	    return false;
	}
	
	@Override
	public String toString() {
		return description.toString();
	}
	
	// Return the specifics of the task (with or without details of time)
	public abstract String getTaskDetails(boolean withTime);
	
    /*
     * Defines an ordering of tasks in a list.
     * Ordering: 1. Pinned 2. Overdue 3. Floating 4. Date order
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
	@Override
	public int compareTo(Task other) {
	    
	    // For now: this is very subclass dependant - assert these conditions
	    assert (this instanceof FloatingTask) || (this instanceof DeadlineTask) || (this instanceof EventTask);
	    
	    // Pinned tasks are the highest priority
		if (this.isPinned() && !other.isPinned()) {
			return -1;
		} else if (!this.isPinned() && other.isPinned()) {
			return 1;
		} 

		// Compare overdue-ness between tasks
        if (this.isOverdue() && !other.isOverdue()) {
            return -1;
        } else if (!this.isOverdue() && other.isOverdue()) {
            return 1;
        }
	      
	    // Floating tasks should come first
        if (this instanceof FloatingTask && !(other instanceof FloatingTask)) {
            return -1;
        } else if (!(this instanceof FloatingTask) && other instanceof FloatingTask) {
            return 1;
        } else if (this instanceof FloatingTask && other instanceof FloatingTask) {
            // If both are floating tasks - they are equal
            return 0;
        }
        
        // Depending on the class type - get the date that we want to compare
        Date myDateToCompare = this instanceof DeadlineTask ? ((DeadlineTask)this).getDeadline() : ((EventTask)this).getStartDate();
        Date otherDateToCompare = other instanceof DeadlineTask ? ((DeadlineTask)other).getDeadline() : ((EventTask)other).getStartDate();
        
        // Sort based on date
        return myDateToCompare.compareTo(otherDateToCompare);
	}
	
}
