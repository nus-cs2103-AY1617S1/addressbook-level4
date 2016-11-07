package tars.logic.commands;

// @@author A0139924W
/**
 * Undo an undoable command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successfully.\n%1$s";
    public static final String MESSAGE_UNSUCCESS = "Undo unsuccessfully.\n%1$s";
    public static final String MESSAGE_EMPTY_UNDO_CMD_HIST =
            "No more actions that can be undo.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo a previous command\n" + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        assert model != null;

        if (model.getUndoableCmdHist().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY_UNDO_CMD_HIST);
        }

        UndoableCommand command =
                (UndoableCommand) model.getUndoableCmdHist().pop();
        model.getRedoableCmdHist().push(command);

        return command.undo();
    }

}
