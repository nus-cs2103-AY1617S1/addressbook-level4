package seedu.address.logic.commands.taskcommands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.commons.events.ui.ShowAliasListEvent;
import seedu.address.logic.commands.CommandResult;

/**
 * Lists all tasks in the TaskManager to the user.
 */
public class ListTaskCommand extends TaskCommand {
    
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_NOTASKS = "No tasks to list";
    public static final String MESSAGE_ALIAS_SUCCESS = "Listed all aliases";
    public static final String MESSAGE_USAGE = "List Tasks: \t" + "list";
    
    public final String argument;
    
    public ListTaskCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public CommandResult execute() {
        if(argument.equals("alias")) {
            EventsCenter.getInstance().post(new ShowAliasListEvent());
            EventsCenter.getInstance().post(new HideHelpRequestEvent());
            return new CommandResult(MESSAGE_ALIAS_SUCCESS);

        }
        else {

            model.clearTasksFilter();
            if(model.getCurrentFilteredTasks().size() == 0) {
                return new CommandResult(MESSAGE_NOTASKS);
            }
            EventsCenter.getInstance().post(new HideHelpRequestEvent());
            return new CommandResult(MESSAGE_SUCCESS);
        }

    }

}