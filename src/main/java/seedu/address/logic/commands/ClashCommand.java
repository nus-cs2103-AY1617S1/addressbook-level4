package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

public class ClashCommand extends Command {
	
	public static final String COMMAND_WORD = "clash";
	
	public static final String MESSAGE_CLASHING_TASKS_LIST_OVERVIEW = "There are %1$d tasks clashing!";
	
	public ClashCommand() {}
	
	@Override
    public CommandResult execute() {
		try{
			model.updateFilteredListToShowClashing();
		}
		catch (DuplicateTaskException dte){
			
		}
        return new CommandResult(String.format(MESSAGE_CLASHING_TASKS_LIST_OVERVIEW, model.getFilteredPersonList().size()));
    }
	
	
}
