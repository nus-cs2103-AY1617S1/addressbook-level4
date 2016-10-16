package seedu.menion.logic.commands;

import seedu.menion.model.ActivityManager;

/**
 * Undo the previous command.
 * Only applies to add, delete and clear
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo-ed previous changes";
    private final Command previousCommand;

    public UndoCommand(Command previousCommand) {
    		this.previousCommand = previousCommand;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        previousCommand.undo();
        return new CommandResult(MESSAGE_SUCCESS);
    }


	@Override
	public void undo() {
		// TODO Auto-generated method stub	
	}
}