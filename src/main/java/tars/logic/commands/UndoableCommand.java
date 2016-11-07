package tars.logic.commands;

// @@author A0139924W
/**
 * Represents a undoable command with hidden internal logic and the ability to be executed.
 */
public abstract class UndoableCommand extends Command {

    public abstract CommandResult undo();

    public abstract CommandResult redo();
}
