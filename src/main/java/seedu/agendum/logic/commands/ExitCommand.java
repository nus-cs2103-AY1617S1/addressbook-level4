package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.EventsCenter;
import seedu.agendum.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "exit";
    public static String COMMAND_FORMAT = "exit";
    public static String COMMAND_DESCRIPTION = "exit Agendum";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Agendum as requested ...";
    
    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public String getName() {
        return COMMAND_WORD;
    }

    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
