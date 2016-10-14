package seedu.address.logic.commands;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeToListDoneViewEvent;
import seedu.address.commons.events.ui.ChangeToListUndoneViewEvent;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String TOOL_TIP = "list";

    public static final String MESSAGE_SUCCESS = "Listed all items";
    public static final String DONE_MESSAGE_SUCCESS = "Listed all done items";
    

    Boolean isListDoneCommand;
    
    public ListCommand(Boolean isListDoneCommand) {
        this.isListDoneCommand = isListDoneCommand;
    }

    @Override
    public CommandResult execute() {
        if (isListDoneCommand) {
            EventsCenter.getInstance().post(new ChangeToListDoneViewEvent());
            return new CommandResult(DONE_MESSAGE_SUCCESS);
        } else {
            EventsCenter.getInstance().post(new ChangeToListUndoneViewEvent());
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
    
}
