package seedu.address.model.task;

import seedu.address.model.Copiable;

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
	
	@Override
	public String toString() {
		return description.toString();
	}
	
	
	/*
	 * Defines an ordering where pind tasks are always appear at the start
	 * of an ordered list of tasks as opposed to non-pind tasks
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task other) {
		if (this.isPinned() && !other.isPinned()) {
			return -1;
		} else if (!this.isPinned() && other.isPinned()) {
			return 1;
		} else {
			// both are pin/not-pin - considered equal
			return 0;
		}
	}
	
}
