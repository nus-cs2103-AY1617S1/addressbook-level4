package tars.logic.commands;

/**
 * Redo an undoable command.
 * 
 * @@author A0139924W
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redo successfully.\n%1$s";
    public static final String MESSAGE_UNSUCCESS = "Redo unsuccessfully.\n%1$s";
    
    public static final String MESSAGE_EMPTY_REDO_CMD_HIST = "No more actions that can be redo.";

    @Override
    public CommandResult execute() {
        assert model != null;

        if (model.getRedoableCmdHist().size() == 0) {
            return new CommandResult(MESSAGE_EMPTY_REDO_CMD_HIST);
        }

        UndoableCommand command = (UndoableCommand) model.getRedoableCmdHist().pop();
        model.getUndoableCmdHist().push(command);
        return command.redo();
    }
}
