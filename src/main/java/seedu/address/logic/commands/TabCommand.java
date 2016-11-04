package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ViewTabRequestEvent;

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
        EventsCenter.getInstance().post(new ViewTabRequestEvent(tabTo));
    	switch (tabTo) {
    	case TODAY:
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case TOMORROW:
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case WEEK:
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case MONTH:
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	case SOMEDAY:
    		return new CommandResult(String.format(MESSAGE_SUCCESS, tabTo.name().toLowerCase()));
    	default:
    		return new CommandResult(MESSAGE_FAIL);
    	}
    }

}
