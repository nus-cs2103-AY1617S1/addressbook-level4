package seedu.address.model.task;

/*
 * Represents a highly general Task object to be subclassed
 */
public abstract class Task implements FavoritableTask {
	/*
	 * All tasks are required to minimally have a description
	 */
	protected Description description;
		
	/*
	 * Indicates if this task is favorited
	 */
	protected boolean favorite = false;
	
	
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
	
}
