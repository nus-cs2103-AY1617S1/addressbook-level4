package seedu.task.logic.commands;

//@@author A0153411W
/**
 * Show last executed commands. 
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows history of last executed commands.\n"
            + "Example: " + COMMAND_WORD;

    public static final String HISTORY_EMPTY = "No commands was executed.";
    
    public HistoryCommand() {}

    @Override
    public CommandResult execute() {
    	return null;
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