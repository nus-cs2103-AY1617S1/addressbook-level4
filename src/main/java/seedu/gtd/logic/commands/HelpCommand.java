package seedu.gtd.logic.commands;


import seedu.gtd.commons.core.EventsCenter;
import seedu.gtd.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Parameters: COMMANDWORD\n"
            + "Example: " + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    
    public final String argument;

    public HelpCommand(String arg) {
    	this.argument=arg;
    }

    @Override
    public CommandResult execute() {
    	
    	switch (argument) {
    	
    	case AddCommand.COMMAND_WORD:
            return new CommandResult(AddCommand.MESSAGE_USAGE);

        case SelectCommand.COMMAND_WORD:
        	return new CommandResult(SelectCommand.MESSAGE_USAGE);

        case DeleteCommand.COMMAND_WORD:
        	return new CommandResult(DeleteCommand.MESSAGE_USAGE);

        case ClearCommand.COMMAND_WORD:
        	return new CommandResult(ClearCommand.MESSAGE_USAGE);

        case FindCommand.COMMAND_WORD:
        	return new CommandResult(FindCommand.MESSAGE_USAGE);

        case ListCommand.COMMAND_WORD:
        	return new CommandResult(ListCommand.MESSAGE_USAGE);

        case ExitCommand.COMMAND_WORD:
        	return new CommandResult(ExitCommand.MESSAGE_USAGE);
        	
        case HelpCommand.COMMAND_WORD:
        	EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        	return new CommandResult(MESSAGE_USAGE+"/n"+SHOWING_HELP_MESSAGE);
        	
        default:
        	EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(MESSAGE_USAGE+"/n"+SHOWING_HELP_MESSAGE);
    	}
    }
}
