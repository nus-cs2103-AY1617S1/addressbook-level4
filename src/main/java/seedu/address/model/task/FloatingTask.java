package seedu.address.model.task;

/*
 * A simple Task implementation that does not have a deadline
 */
public class FloatingTask extends Task implements FavoritableTask, CompletableTask {

	public FloatingTask(Description description) {
		super(description);
	}

	public FloatingTask(String descriptionText) {
		this(new Description(descriptionText));
	}
	
	@Override
	public FloatingTask copy() {
		Description newDescription = new Description(this.description.getContent());
		FloatingTask newTask = new FloatingTask(newDescription);
		if (this.isFavorite()) {
			newTask.setAsFavorite();
		} else {
			newTask.setAsNotFavorite();
		}
		return newTask;
	 }
	
	@Override
	public String toString() {
		return String.format("[Floating Task][Description: %s]", description);
	}
}
