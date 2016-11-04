package seedu.address.logic.commands;

//@@author A0093960X
/**
 * Undoes the latest UndoableCommand that was recorded in the undoable command
 * history.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the last reversible command, reversing the effect on the task manager.\n" + "Example: "
            + COMMAND_WORD;

    public static final String TOOL_TIP = "undo";

    public static final String MESSAGE_FAILURE = "There is nothing to undo.";

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;

        if (history.isEarliestCommand()) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        UndoableCommand cmdToUndo = history.undoStep();
        return cmdToUndo.undo();

    }

}
