package seedu.taskitty.logic.commands;


import seedu.taskitty.commons.core.EventsCenter;
import seedu.taskitty.commons.events.ui.ShowHelpRequestEvent;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "Am I not good enough, Meow?";
    public static final String MESSAGE_ERROR = "Use \"" + COMMAND_WORD + "\" for more information";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    @Override
    public void saveStateIfNeeded(String commandText) {}
}
