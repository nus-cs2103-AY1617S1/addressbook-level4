package seedu.task.logic.commands;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.SwitchCommandBoxFunctionEvent;

public class SearchCommand extends Command {
    
    public static final String MESSAGE_SEARCH_SUCCESS = "Live Search activated!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SwitchCommandBoxFunctionEvent());
        return new CommandResult(true, MESSAGE_SEARCH_SUCCESS);
    }
}
