package seedu.task.logic.commands;

/**
 * @@author A0147944U
 * Lists all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to specified parameters."
            + "If parameter is not given, tasks will be sorted by a default preset.\n"
            + "Parameters: PARAMETER\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with order: %1$s";
    
    private final String keyword;
    
    /**
     * Parse the keyword given by user based on first character of the input
     */
    public SortCommand(String keyword) {
        if (keyword.startsWith("d")) { //deadline
            this.keyword = "Deadline";
        } else if (keyword.startsWith("s")) { //start time
            this.keyword = "Start Time";
        } else if (keyword.startsWith("e")) { //end time
            this.keyword = "End Time";
        } else if (keyword.startsWith("c")) { //done status
            this.keyword = "Completed";
        } else if (keyword.startsWith("n")) { //name
            this.keyword = "Name";
        } else { //default sorting
            this.keyword = "Default";
        }
    }

    /**
     * Executes 'sortFilteredTaskList' and returns success message
     */
    @Override
    public CommandResult execute(boolean isUndo) {
            model.sortFilteredTaskList(keyword);
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
