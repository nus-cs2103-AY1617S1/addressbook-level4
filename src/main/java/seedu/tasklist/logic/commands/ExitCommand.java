package seedu.tasklist.logic.commands;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.events.ui.ExitAppRequestEvent;
import seedu.tasklist.logic.parser.CommandParser;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task List as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public Command prepare(String args) {
        return new ExitCommand();
    }

}
