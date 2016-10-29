package seedu.ggist.logic.commands;

import seedu.ggist.commons.core.EventsCenter;
import seedu.ggist.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting GGist as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        indicateCorrectCommandExecuted();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
