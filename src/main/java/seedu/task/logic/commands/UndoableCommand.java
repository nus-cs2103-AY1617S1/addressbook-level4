package seedu.task.logic.commands;

/**
 * Interface for a command that can be undone
 * @author Syed Abdullah
 *
 */
public abstract class UndoableCommand extends Command {
    /**
     * Reverse changes made by command and returns the result message.
     *
     * @return feedback message of the operation result for display
     */
    public abstract CommandResult rollback();
}
