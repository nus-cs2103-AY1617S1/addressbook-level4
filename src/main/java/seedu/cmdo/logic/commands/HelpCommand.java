package seedu.cmdo.logic.commands;


import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.events.ui.ShowHelpEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
