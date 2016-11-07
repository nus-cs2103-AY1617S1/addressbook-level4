package w15c2.tusk.model.task;

//@@author A0143107U
/*
 * Tasks that implement this interface can be completed
 */
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
	 
	public boolean isCompleted();

}
