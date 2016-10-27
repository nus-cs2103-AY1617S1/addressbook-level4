package tars.logic.commands;

/**
 * Undo an undoable command.
 * 
 * @@author A0139924W
 */
public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo successfully.";
    public static final String MESSAGE_UNSUCCESS = "Undo unsuccessfully.";
    public static final String MESSAGE_EMPTY_UNDO_CMD_HIST = "No more actions that can be undo.";

    @Override
    public CommandResult execute() {
        assert model != null;

        if (model.getUndoableCmdHist().size() == 0) {
            return new CommandResult(MESSAGE_EMPTY_UNDO_CMD_HIST);
        }

        UndoableCommand command = (UndoableCommand) model.getUndoableCmdHist().pop();
        model.getRedoableCmdHist().push(command);
        
        return command.undo();
    }

}
