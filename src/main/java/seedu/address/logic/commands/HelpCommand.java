package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;

//@@author A0093960X
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
            + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final String TOOL_TIP = "help";

    @Override
    public CommandResult execute() {
        postNewHelpEvent();
        return new CommandResult(SHOWING_HELP_MESSAGE);
    }
    
    /**
     * Posts a new help request event
     */
    private void postNewHelpEvent() {
        EventsCenter theEventsCenter = EventsCenter.getInstance();
        ShowHelpRequestEvent newHelpRequest = new ShowHelpRequestEvent();
        theEventsCenter.post(newHelpRequest);
    }
}