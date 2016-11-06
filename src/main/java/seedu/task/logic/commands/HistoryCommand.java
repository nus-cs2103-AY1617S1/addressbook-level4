package seedu.task.logic.commands;

//@@author A0153411W
/**
 * Show last executed commands. 
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows history of last executed commands.\n"
            + "Example: " + COMMAND_WORD;
    
    public HistoryCommand() {}

    @Override
    public CommandResult execute() {
    	String userHistory= model.getCommandHistory();
    	return new CommandResult(userHistory);
    }

	@Override
	public CommandResult executeUndo() {
		return null;
	}

	@Override
	public boolean isReversible() {
		return false;
	}
	
}
//@@author