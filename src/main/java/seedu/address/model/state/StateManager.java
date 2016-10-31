package seedu.address.model.state;

//import java.util.LinkedList;
import java.util.Stack;
import seedu.address.commons.exceptions.FinishStateException;

/**
 * Saves the command list of the task manager.
 */

public class StateManager implements State {
	
	private TaskCommandState currentState;
	private Stack<TaskCommandState> unStates;
	private Stack<TaskCommandState> reStates;
	
	public StateManager(TaskCommandState presentState) {
		currentState = presentState;
		unStates = new Stack<TaskCommandState>(); //Undo states
		reStates = new Stack<TaskCommandState>(); //Revert states
	}
	
	@Override
	public void currentState(TaskCommandState state) {
		if(unStates.size() == 10) {
			unStates.pop();
		}
		
		unStates.add(currentState);
		currentState = state;
		reStates.clear();
	}
	
	@Override
	public TaskCommandState getPreviousState() throws FinishStateException {
		if(unStates.size() == 10) {
			throw new FinishStateException();
		}
		
		reStates.add(currentState);
		currentState = unStates.pop();
		return currentState;
	}
	
	@Override
	public TaskCommandState getInitialState() throws FinishStateException {
		if(reStates.size() == 10) {
			throw new FinishStateException();
		}
		
		unStates.push(currentState);
		currentState = reStates.pop();
		return currentState;
	}
	
}