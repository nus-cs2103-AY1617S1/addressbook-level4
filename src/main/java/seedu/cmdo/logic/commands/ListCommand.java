package seedu.cmdo.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_SHORT_ALL = "la";
    public static final String COMMAND_WORD_SHORT_DONE = "ld";
    public static final String COMMAND_WORD_ALL = "list all";
    public static final String COMMAND_WORD_DONE = "list done";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final boolean taskStatus;

    public ListCommand(boolean taskStatus) {
    	this.taskStatus = taskStatus;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll(taskStatus);
        if (taskStatus) {
        	return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
