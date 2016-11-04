package seedu.address.history;

import seedu.address.logic.commands.UndoableCommand;

//@@author A0093960X
/**
 * API of the UndoableCommandHistory Component.
 */
public interface UndoableCommandHistory {

    /**
     * Updates the UndoableCommand history with the given UndoableCommand
     * 
     * @param undoableCommand the UndoableCommand to update the history with
     */
    public void updateCommandHistory(UndoableCommand undoableCommand);

    /**
     * Returns whether we are already at the earliest command state (there is
     * nothing to undo anymore)
     * 
     * @return boolean representing whether we are already at the earliest
     *         command of the undoable command history
     */
    public boolean isEarliestCommand();

    /**
     * Returns whether we are already at the latest command state (there is
     * nothing to redo anymore)
     * 
     * @return boolean representing whether we are already at the latest command
     *         of the redoable command history
     */
    public boolean isLatestCommand();

    /**
     * Executes an undo step on the command history, returning the
     * UndoableCommand that was undone
     * 
     * @return the UndoableCommand that was undone
     */
    public UndoableCommand undoStep();

    /**
     * Executes a redo step on the command history, returning the
     * UndoableCommand that was redone
     * 
     * @return the UndoableCommand that was redone
     */
    public UndoableCommand redoStep();

}
