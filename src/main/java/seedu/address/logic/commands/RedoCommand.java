package seedu.address.logic.commands;

import seedu.address.logic.URManager.Context;
import seedu.address.logic.URManager.NoAvailableCommandException;

public class RedoCommand extends Command{

	public static final String COMMAND_WORD = "r";
	
    public static final String MESSAGE_FAIL = "No command to redo.";

    public RedoCommand() {}

    @Override
    public CommandResult execute() {
    	
    	try {
    		Context contextToRedo = urManager.getContextToRedo();
    		urManager.addToUndoQueueUsedByRedo(model, contextToRedo.getCommand());
    		return contextToRedo.getCommand().execute();
        } catch (NoAvailableCommandException nace){
        	return new CommandResult(MESSAGE_FAIL);
        }
    }

}
