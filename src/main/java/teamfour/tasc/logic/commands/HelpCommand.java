package teamfour.tasc.logic.commands;


import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.ShowHelpRequestEvent;
import teamfour.tasc.model.keyword.HelpCommandKeyword;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = HelpCommandKeyword.keyword;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public HelpCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
