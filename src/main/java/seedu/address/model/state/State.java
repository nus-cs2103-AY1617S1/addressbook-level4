package seedu.address.model.state;

import seedu.address.commons.exceptions.FinishStateException;

/**
 * State Component API
 */

public interface State {
	
	/** Takes in the current state of the command list in the task manager.*/
	public void currentState(TaskCommandState state);
	
	/** Gets the previous state of the command list of the task manager.*/
	public TaskCommandState getPreviousState() throws FinishStateException;
	
	/** Gets back to the initial state of the command list of the task manager.*/
	public TaskCommandState getInitialState() throws FinishStateException;
	
}