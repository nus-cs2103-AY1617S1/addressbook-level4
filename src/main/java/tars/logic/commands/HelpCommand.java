package tars.logic.commands;

import tars.commons.core.EventsCenter;
import tars.commons.events.ui.ShowHelpRequestEvent;

/**
 * @@author A0140022H
 * 
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Shows program usage instructions in help panel.\n"
                    + "Parameters: [COMMAND WORD]\n" + "Example: "
                    + COMMAND_WORD + " add";

    public static final String SHOWING_HELP_MESSAGE =
            "Switched to Help tab pane.";

    private String args;

    public HelpCommand(String args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent(args));
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
}
