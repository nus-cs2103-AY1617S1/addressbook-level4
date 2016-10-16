package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;

/**
 * Undo the previous command.
 * Only applies to add, delete and clear
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo-ed previous changes";
    public static final String MESSAGE_FAILURE = "Previous changes cannot be undo";
    private final Command previousCommand;

    public UndoCommand(Command previousCommand) {
    		this.previousCommand = previousCommand;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        boolean ableToUndo = previousCommand.undo();
        if (ableToUndo) {
        	return new CommandResult(MESSAGE_SUCCESS);
        }
        else {
        	return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /*
     * undo command does not support undo
     */
	@Override
	public boolean undo() {
		return false;
	}
}