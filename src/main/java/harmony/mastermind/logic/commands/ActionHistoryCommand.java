package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.EventsCenter;
import harmony.mastermind.commons.events.ui.ToggleActionHistoryEvent;

//@@author A0138862W
/**
 * 
 * Command to toggle ActionHistory in UI
 * 
 * This command takes no parameter.
 * @author kfwong
 *
 */
public class ActionHistoryCommand extends Command {

    public static final String COMMAND_KEYWORD_ACTIONHISTORY = "actionhistory";
    public static final String COMMAND_DESCRIPTION = "Toggles action history bar";

    public static final String MESSAGE_SUCCESS = "Action history toggled.";

    @Override
    public CommandResult execute() {

        requestToggleActionHistory();
        return new CommandResult(COMMAND_KEYWORD_ACTIONHISTORY, MESSAGE_SUCCESS);

    }

    private void requestToggleActionHistory() {
        EventsCenter.getInstance().post(new ToggleActionHistoryEvent());
    }

}
