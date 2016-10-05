package seedu.address.model.task;

/*
 * Represents a highly general Task object to be subclassed
 */
public abstract class Task {
	/*
	 * All tasks are required to minimally have a description
	 */
	protected Description description;
	
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
