package seedu.oneline.logic.commands;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.events.ui.ExitAppRequestEvent;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Book as requested ...";

    public ExitCommand() {}

    public static ExitCommand createFromArgs(String args) {
        return new ExitCommand();
    }
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
