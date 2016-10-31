package seedu.address.model.task;

//@@author A0138978E
public interface PinnableTask {
	
	/*
	 * Gives the task a 'pin' status
	 */
	public void setAsPin();
	
	/*
	 * Removes the task's 'pin' status
	 */
	public void setAsNotPin();
	
	/*
	 * Checks if a task is pinned
	 */
	public boolean isPinned();
}
