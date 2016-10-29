package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.commands.CommandResult;

/**
 * Terminates the program.
 */
//@@author A0143107U
public class ExitCommand extends TaskCommand {

    public static final String[] COMMAND_WORD = {"exit"};

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Tusk Manager as requested ...";
    public static final String MESSAGE_USAGE = COMMAND_WORD[0] + ": Exits Tusk Manager.\n"
            + "Example: " + COMMAND_WORD[0];
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}