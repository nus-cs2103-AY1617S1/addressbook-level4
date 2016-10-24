package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.ExitAppRequestEvent;
import teamfour.tasc.model.keyword.ExitCommandKeyword;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = ExitCommandKeyword.keyword;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TaSc as requested ...";

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
