package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Schema as requested ...";
    
    public static final String COMMAND_SUMMARY = "Exitting Mastermind:"
            + "\n" + COMMAND_WORD;

    public ExitCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(COMMAND_WORD, MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
