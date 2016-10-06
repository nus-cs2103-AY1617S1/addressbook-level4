package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    private final String commandWord;

    /**
     * Shows general help
     */
    public HelpCommand() {
        commandWord = "";
    }

    /**
     * Shows help for specific command word {@param commandWord}
     */
    public HelpCommand(String commandWord) {
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model, EventsCenter eventsCenter) {
        eventsCenter.post(new ShowHelpRequestEvent(commandWord));
        return new CommandResult(Messages.MESSAGE_HELP_WINDOW_SHOWN);
    }
}
