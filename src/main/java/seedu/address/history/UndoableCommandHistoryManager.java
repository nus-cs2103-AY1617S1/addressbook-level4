package seedu.address.history;

import java.util.Stack;

import seedu.address.logic.commands.UndoableCommand;

//@@author A0093960X
/**
 * Stores the history of undoable and redoable commands.
 */
public class UndoableCommandHistoryManager implements UndoableCommandHistory {

    private static UndoableCommandHistoryManager theUndoableCommandHistory;

    // command effects
    private Stack<UndoableCommand> undoableCommands;
    private Stack<UndoableCommand> redoableCommands;

    // Private constructor for Singleton Pattern
    private UndoableCommandHistoryManager() {
        undoableCommands = new Stack<UndoableCommand>();
        redoableCommands = new Stack<UndoableCommand>();
    }

    // Use Singleton Pattern here
    public static UndoableCommandHistoryManager getInstance() {
        if (theUndoableCommandHistory == null) {
            theUndoableCommandHistory = new UndoableCommandHistoryManager();
        }
        return theUndoableCommandHistory;
    }

    @Override
    public void updateCommandHistory(UndoableCommand undoableCommand) {
        assert undoableCommands != null;
        undoableCommands.push(undoableCommand);
        resetRedo();
    }

    @Override
    public boolean isEarliestCommand() {
        assert undoableCommands != null;
        return undoableCommands.isEmpty();
    }

    @Override
    public boolean isLatestCommand() {
        assert redoableCommands != null;
        return redoableCommands.isEmpty();
    }

    @Override
    public UndoableCommand undoStep() {
        assert redoableCommands != null && undoableCommands != null;
        return redoableCommands.push(undoableCommands.pop());
    }

    @Override
    public UndoableCommand redoStep() {
        assert redoableCommands != null && undoableCommands != null;
        return undoableCommands.push(redoableCommands.pop());
    }

    /**
     * Resets the redoable command history
     */
    private void resetRedo() {
        redoableCommands = new Stack<UndoableCommand>();
    }

}
