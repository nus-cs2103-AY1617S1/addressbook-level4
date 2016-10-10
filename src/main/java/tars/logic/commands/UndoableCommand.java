package tars.logic.commands;

/**
 * Represents a undoable command with hidden internal logic and the ability to be executed.
 */
public abstract class UndoableCommand extends Command {
    
    public abstract CommandResult undo();
}
