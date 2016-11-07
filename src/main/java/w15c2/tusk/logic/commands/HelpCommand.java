package w15c2.tusk.logic.commands;

import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.ShowHelpRequestEvent;

//@@author A0139708W
/**
 * Shows Help for Commands
 */
public class HelpCommand extends Command {
    public static final String COMMAND_WORD = "help";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_FORMAT = COMMAND_WORD;
    public static final String COMMAND_DESCRIPTION = "Help"; 

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help panel.";

    /**
     * Shows help and gives user feedback.
     * 
     * @return  Result of command.
     */
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
    
}
