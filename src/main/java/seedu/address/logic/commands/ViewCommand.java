package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.model.ViewCategoryChangedEvent;
import seedu.address.commons.events.ui.MinimizeRequestEvent;

//@@author A0135767U
/**
 * Lists all tasks in the SmartyDo to the user.
 */
public class ViewCommand extends Command {
	
	private final String keyword;
	private final String ARGUMENT_ALL = "all";
	private final String ARGUMENT_OVERDUE = "overdue";
	private final String ARGUMENT_UPCOMING = "upcoming";
	private final String ARGUMENT_COMPLETED = "completed";
	private final String ARGUMENT_INCOMPLETE = "incomplete";

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Viewing %1$s tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of all tasks with a filter.\n"
            + "Parameters: [ALL/COMPLETED/UPCOMING/OVERDUE]\n"
            + "Example: " + COMMAND_WORD + " all";

    public ViewCommand(Set<String> keywords) {
    	this.keyword = keywords.toArray(new String[keywords.size()])[0].toLowerCase();
    }

    @Override
    public CommandResult execute() {
    	
    	switch(keyword) {
    	case ARGUMENT_ALL:
    		model.updateFilteredListToShowAll();
    		break;
    	case ARGUMENT_OVERDUE:
    		model.updateFilteredListToShowOverdue();
    		break;
    	case ARGUMENT_UPCOMING:
    		model.updateFilteredListToShowUpcoming();
    		break;
    	case ARGUMENT_COMPLETED:
    		model.updateFilteredListToShowCompleted(true);
    		break;
    	case ARGUMENT_INCOMPLETE:
    		model.updateFilteredListToShowCompleted(false);
    		break;
    	default:
        	indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_ARGUMENT);
    	}
    	
    	EventsCenter.getInstance().post(new MinimizeRequestEvent());
    	EventsCenter.getInstance().post(new ViewCategoryChangedEvent(keyword));
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }
}
