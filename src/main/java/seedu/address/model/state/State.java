package seedu.address.model.state;

import seedu.address.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * The API of the State component.
 */
public interface State {
    
    /** Saves the current state of the task manager. */
    public void saveState(TaskManagerState state);
    
    /** Gets the previous state of the task manager. */
    public TaskManagerState getPreviousState() throws StateLimitException;
    
    /** Gets the next state of the task manager. */
    public TaskManagerState getNextState() throws StateLimitException;
    
}
