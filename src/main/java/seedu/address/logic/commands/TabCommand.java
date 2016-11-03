package seedu.address.logic.commands;

//@@author A0141019U
public class TabCommand extends Command {
	
	public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the tab on the right hand pane "
            + "corresponding to the time period input.\n"
            + "Parameters: (today | tomorrow | week | month | someday)\n"
            + "Example: " + COMMAND_WORD + " tomorrow";
    
    public static final String MESSAGE_SUCCESS = "Switched to %1$s tab.";
    public static final String MESSAGE_FAIL = "Invalid tab name.";
    
    public static enum TabName {
    	TODAY, TOMORROW, WEEK, MONTH, SOMEDAY
    }
    
    TabName tabTo;
    public TabCommand(TabName tabTo) {
    	this.tabTo = tabTo;
    }

    @Override
    public CommandResult execute() {
    	switch (tabTo) {
    	case TODAY:
    		//TODO
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case TOMORROW:
    		//TODO
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case WEEK:
    		//TODO
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case MONTH:
    		//TODO
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case SOMEDAY:
    		//TODO
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	default:
    		return new CommandResult(MESSAGE_FAIL);
    	}
    }

}
