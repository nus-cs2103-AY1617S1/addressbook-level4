package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class ClashCommand extends Command {
	
	public static final String COMMAND_WORD = "clash";
	
	public static final String MESSAGE_SUCCESS = "Listed all clashing tasks in the current list.";
	
	public ClashCommand() {}
	
	@Override
    public CommandResult execute() {
		try{
			model.updateFilteredListToShowClashing();
		}
		catch (DuplicateTaskException dte){
			
		}
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
	
	
}
