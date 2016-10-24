package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeToListDoneViewEvent;
import seedu.address.commons.events.ui.ChangeToListUndoneViewEvent;

/**
 * Lists all tasks in the task manager to the user.
 * Supports the listing of all undone, or all done tasks.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String TOOL_TIP = "list [done]";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String DONE_MESSAGE_SUCCESS = "Listed all done tasks";
        
    private Boolean isListDoneCommand;
    
    public ListCommand(Boolean isListDoneCommand) {
        this.isListDoneCommand = isListDoneCommand;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListsToShowAll();
        if (isListDoneCommand) {
            model.setCurrentListToBeDoneList();
            return new CommandResult(DONE_MESSAGE_SUCCESS);
        } else {
            model.setCurrentListToBeUndoneList();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
    
}
