package seedu.address.model.task;

import seedu.address.model.Copiable;

/*
 * Represents a highly general Task object to be subclassed
 */
public abstract class Task implements FavoritableTask, CompletableTask, Comparable<Task>, Copiable<Task> {
	/*
	 * All tasks are required to minimally have a description
	 */
	protected Description description;
		
	/*
	 * Indicates if this task is favorited
	 */
	protected boolean favorite = false;
	
	/*
	 * Indicates if this task is completed
	 */
	protected boolean complete = false;
	
	
	@Override
	public void setAsFavorite() {
		this.favorite = true;
	}
	
	@Override
	public void setAsNotFavorite() {
		this.favorite = false;
	}
	
	@Override
	public boolean isFavorite() {
		return this.favorite;
	}
	
	@Override
	public void setAsComplete() {
		this.complete = true;
	}
	
	@Override
	public void setAsUncomplete() {
		this.complete = false;
	}
	
	@Override
	public boolean isComplete(){
		return this.complete;
	}
	
	
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
	 * Defines an ordering where favorited tasks are always appear at the start
	 * of an ordered list of tasks as opposed to non-favorited tasks
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Task other) {
		if (this.isFavorite() && !other.isFavorite()) {
			return -1;
		} else if (!this.isFavorite() && other.isFavorite()) {
			return 1;
		} else {
			// both are favorite/not-favorite - considered equal
			return 0;
		}
	}
	
}
