package seedu.toDoList.model.state;

import java.util.LinkedList;

import seedu.toDoList.commons.core.ComponentManager;
import seedu.toDoList.commons.events.storage.RedoStoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.StoragePathChangedEvent;
import seedu.toDoList.commons.events.storage.UndoStoragePathChangedEvent;
import seedu.toDoList.commons.exceptions.StateLimitException;

//@@author A0146123R
/**
 * Saves the states of the task manager.
 */
public class StateManager extends ComponentManager implements State {

    private static final int MAX = 10; // Undo/Redo up to 10 times
    private static final int MIN = 1;

    private TaskManagerState currentState;
    private LinkedList<TaskManagerState> undoStates;
    private LinkedList<TaskManagerState> redoStates;

    private int undoFilePathAvailable;
    private int redoFilePathAvailable;

    /**
     * Initializes a StateManager with the given TaskManagerState.
     * TaskManagerState should not be null.
     */
    public StateManager(TaskManagerState initialState) {
        assert initialState != null;

        currentState = initialState;
        undoStates = new LinkedList<TaskManagerState>();
        redoStates = new LinkedList<TaskManagerState>();
        undoFilePathAvailable = 0;
        redoFilePathAvailable = 0;
    }

    public void saveState(TaskManagerState state) {
        assert state != null;

        if (undoStates.size() == MAX) {
            undoStates.removeFirst();
        }
        undoStates.add(currentState);
        currentState = state;
        redoStates.clear();

        assert undoStates.size() <= MAX;
    }

    @Override
    public TaskManagerState getPreviousState() throws StateLimitException {
        if (undoStates.size() < MIN) {
            throw new StateLimitException();
        }
        redoStates.add(currentState);
        currentState = undoStates.removeLast();
        return currentState;
    }

    @Override
    public TaskManagerState getNextState() throws StateLimitException {
        if (redoStates.size() < MIN) {
            throw new StateLimitException();
        }
        undoStates.add(currentState);
        currentState = redoStates.removeLast();
        return currentState;
    }

    @Override
    public void saveFilePath(String filePath, boolean isToClearOld) {
        assert filePath != null;

        raise(new StoragePathChangedEvent(filePath, isToClearOld));
        undoFilePathAvailable++;
        redoFilePathAvailable = 0;
    }

    @Override
    public void getPreviousFilePath(boolean isToClearNew) throws StateLimitException {
        if (undoFilePathAvailable < MIN) {
            throw new StateLimitException();
        }
        raise(new UndoStoragePathChangedEvent(isToClearNew));
        undoFilePathAvailable--;
        redoFilePathAvailable++;
    }

    @Override
    public void getNextFilePath() throws StateLimitException {
        if (redoFilePathAvailable < MIN) {
            throw new StateLimitException();
        }
        raise(new RedoStoragePathChangedEvent());
        undoFilePathAvailable++;
        redoFilePathAvailable--;
    }

}