package seedu.taskmanager.logic.commands;


import seedu.taskmanager.commons.core.EventsCenter;
import seedu.taskmanager.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "h";
    //@@author 
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
