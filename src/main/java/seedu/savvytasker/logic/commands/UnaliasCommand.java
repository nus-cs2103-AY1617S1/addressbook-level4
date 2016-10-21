package seedu.savvytasker.logic.commands;

import seedu.savvytasker.commons.exceptions.IllegalValueException;
import seedu.savvytasker.logic.commands.models.AliasCommandModel;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.model.task.TaskList.DuplicateTaskException;

/**
 * Command to remove aliases
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an alias for a command or parameter. "
            + "Parameters: s/SHORT_KEYWORD\n"
            + "Example: " + COMMAND_WORD
            + " s/pjm";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNREGOGNIZED_ALIAS = "This alias is not in use";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public UnaliasCommand(AliasCommandModel commandModel) {
        this.toAdd = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            return new CommandResult(MESSAGE_UNREGOGNIZED_ALIAS);
        }

    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

    /**
     * Redo the unalias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean redo() {
        // TODO Auto-generated method stub
        return false;
    }
    /**
     * Undo the unalias command
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean undo() {
        // TODO Auto-generated method stub
        return false;
    }

}
