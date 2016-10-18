package seedu.address.logic.commands;

public class ClashCommand extends Command {
	
	public static final String COMMAND_WORD = "clash";
	
	public static final String MESSAGE_SUCCESS = "Listed all clashing tasks in the current list.";
	
	public ClashCommand() {}
	
	@Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
	
	
}
