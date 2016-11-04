package seedu.address.logic.commands;

//@@author A0093960X
/**
 * Redoes the latest UndoableCommand that was undone.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the last undo command, reversing the effect on the task manager.\n" + "Example: "
            + COMMAND_WORD;

    public static final String TOOL_TIP = "redo";

    public static final String MESSAGE_FAILURE = "There is nothing to redo.";

    public RedoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;

        if (history.isLatestCommand()) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        UndoableCommand cmdToRedo = history.redoStep();
        return cmdToRedo.execute();
    }

}
