package seedu.forgetmenot.logic.commands;

import seedu.forgetmenot.commons.core.EventsCenter;
import seedu.forgetmenot.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting ForgetMeNot as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        model.clearHistory();
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
