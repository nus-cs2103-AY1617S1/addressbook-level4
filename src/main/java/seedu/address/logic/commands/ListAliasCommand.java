package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayAliasListEvent;
import seedu.address.model.TaskManager;

/**
 * Lists all aliases in the alias manager to the user.
 */
public class ListAliasCommand extends Command {

    public static final String COMMAND_WORD = "list-alias";
    public static final String MESSAGE_SUCCESS = "Listed all aliases in alias manager.";

    public ListAliasCommand() {}

    @Override
    public CommandResult execute() {
        assert model != null;
        
        model.saveState();
        
        EventsCenter.getInstance().post(new DisplayAliasListEvent(model.getFilteredAliasList()));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
