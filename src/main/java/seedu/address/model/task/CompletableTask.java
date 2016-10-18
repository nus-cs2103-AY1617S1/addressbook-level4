package seedu.address.model.task;

public interface CompletableTask {
	/*
	 * Gives the task a 'complete' status
	 */
	public void setAsComplete();
	
	/*
	 * Removes the task's 'complete' status
	 */
	 
	public void setAsUncomplete();
	
	/*
	 * Checks if a task is completed
	 */
	 
	public boolean isComplete();

}
