package seedu.taskmanager.logic.commands;

//@@author A0140060A
/**
 * Lists all not done (uncompleted) items in the task manager to the user.
 */
public class ListNotDoneCommand extends Command {

    public static final String COMMAND_WORD = "listnotdone";
    
    public static final String SHORT_COMMAND_WORD = "lnd";
    
    public static final String MESSAGE_SUCCESS = "Listed all uncompleted items";

    public ListNotDoneCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowNotDone();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
