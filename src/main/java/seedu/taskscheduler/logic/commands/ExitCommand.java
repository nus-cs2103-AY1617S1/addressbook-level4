package seedu.taskscheduler.logic.commands;

import seedu.taskscheduler.commons.core.EventsCenter;
import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Scheduler as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public CommandResult revert() {
        // This command not available for revert
        assert false : Messages.MESSAGE_PROGRAM_ERROR;
        return null;
    }

}
