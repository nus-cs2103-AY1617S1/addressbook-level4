package seedu.tasklist.logic.commands;


import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.events.ui.ShowHelpRequestEvent;
import seedu.tasklist.logic.parser.CommandParser;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    @Override
    public Command prepare(String args) {
        return new HelpCommand();
    }
}
