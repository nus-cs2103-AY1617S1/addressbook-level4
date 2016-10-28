package seedu.address.model.state;

import java.util.LinkedList;
import seedu.address.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Saves the states of the task manager.
 */
public class StateManager implements State {
    
    // Undo/Redo up to 5 times
    private static final int CAPACITY = 5;
    
    private TaskManagerState currentState;
    private LinkedList<TaskManagerState> undoStates;
    private LinkedList<TaskManagerState> redoStates;
    
    public StateManager(TaskManagerState initialState) {
        currentState = initialState;
        undoStates = new LinkedList<TaskManagerState>();
        redoStates = new LinkedList<TaskManagerState>();
    }
    
    public void saveState(TaskManagerState state) {
        if (undoStates.size() == CAPACITY) {
            undoStates.removeFirst();
        }
        undoStates.add(currentState);
        currentState = state;
        redoStates.clear();
        
        assert undoStates.size() <= CAPACITY;
    }

    @Override
    public TaskManagerState getPreviousState() throws StateLimitException{
        if (undoStates.size() == 0) {
            throw new StateLimitException();
        }
        redoStates.add(currentState);
        currentState = undoStates.removeLast();
        return currentState;
    }

    @Override
    public TaskManagerState getNextState() throws StateLimitException{
        if (redoStates.size() == 0) {
            throw new StateLimitException();
        }
        undoStates.add(currentState);
        currentState = redoStates.removeLast();
        return currentState;
    }
    
}
