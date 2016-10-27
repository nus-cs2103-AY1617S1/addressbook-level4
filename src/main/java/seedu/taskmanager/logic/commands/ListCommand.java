package seedu.taskmanager.logic.commands;

//@@author A0135792X

/**
 * Lists all items in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "l";
    //@@author 
 
  //@@author A0135792X
    
    public static final String MESSAGE_SUCCESS = "Listed all items";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
