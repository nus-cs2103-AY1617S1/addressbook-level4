package seedu.taskscheduler.logic.commands;


import seedu.taskscheduler.commons.core.EventsCenter;
import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.events.ui.ShowHelpEvent;

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

    //@@author A0148145E
    @Override
    public CommandResult revert() {
        // This command not available for revert
        assert false : Messages.MESSAGE_PROGRAM_ERROR;
        return null;
    }
}
