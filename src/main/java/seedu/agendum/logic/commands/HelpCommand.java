package seedu.agendum.logic.commands;


import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

 // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_FORMAT = "help";
    public static final String COMMAND_DESCRIPTION = "view Agendum’s command summary";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
