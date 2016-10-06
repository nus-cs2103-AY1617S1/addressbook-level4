package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public ExitCommand() {}

    /**
     * Asserts that {@code eventsCenter} is non-null
     */
    @Override
    public CommandResult execute() {
        eventsCenter.post(new ExitAppRequestEvent());
        return new CommandResult(Messages.MESSAGE_EXIT_APPLICATION);
    }
}
