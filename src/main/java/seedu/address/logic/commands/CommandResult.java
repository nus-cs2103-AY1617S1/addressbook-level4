package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeToListUndoneViewEvent;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        
        if (!feedbackToUser.equals(ListCommand.DONE_MESSAGE_SUCCESS)) {
            EventsCenter.getInstance().post(new ChangeToListUndoneViewEvent());
        }
        this.feedbackToUser = feedbackToUser;
    }

}
