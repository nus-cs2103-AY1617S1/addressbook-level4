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

    /** Saves and sets the current file path of the task manager. */
    public void saveFilePath(String filePath, boolean isToClearOld);

    /** Gets and sets the previous file path of the task manager. */
    public void getPreviousFilePath(boolean isToClearNew) throws StateLimitException;

    /** Gets and sets the next file path of the task manager. */
    public void getNextFilePath() throws StateLimitException;

}
