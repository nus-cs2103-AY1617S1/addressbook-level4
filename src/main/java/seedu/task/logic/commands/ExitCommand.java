package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Task Manager as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute(boolean isUndo) {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
