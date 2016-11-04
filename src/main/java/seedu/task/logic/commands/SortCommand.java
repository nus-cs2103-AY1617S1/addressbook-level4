package seedu.task.logic.commands;

// @@author A0147944U
/**
 * Lists all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    
    public static final String COMMAND_WORD_ALT = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to specified parameters.\n"
            + "If parameter is not given, tasks will be sorted by a default preset.\n"
            + "Parameters: PARAMETER\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with order: %1$s";
    
    public static final String MESSAGE_SUCCESS_DEFAULT = "Listed all tasks with order: Default Preset\n"
            + "Tasks are sorted according to these criteria in the order:\n"
            + "Incomplete tasks, Floating tasks, Older tasks,\n"
            + "lastly Name in ascending order.";
    
    public static final String MESSAGE_FAILURE = "Invalid sort parameter given: %1$s\n"
            + "Possible parameters are: default, name/n, starttime/start/s,\n"
            + "endtime/end/e, deadline/dead/d, completed/done/c";

    private final String keyword;
    
    private Boolean invalidKeyword = false;

    /**
     * Parse the keyword given by user based on first character of the input
     * 
     * @param keyword
     *            keyword given by user to sort tasks by
     */
    public SortCommand(String keyword) {
        if (keyword.equals("d") || keyword.equals("deadline") || keyword.equals("dead")) { // deadline
            this.keyword = "Deadline";
        } else if (keyword.equals("s") || keyword.equals("starttime") || keyword.equals("start")) { // start time
            this.keyword = "Start Time";
        } else if (keyword.equals("e") || keyword.equals("endtime") || keyword.equals("end")) { // end time
            this.keyword = "End Time";
        } else if (keyword.equals("c") || keyword.equals("completed") || keyword.equals("done")) { // done status
            this.keyword = "Completed";
        } else if (keyword.equals("n") || keyword.equals("name")) { // name
            this.keyword = "Name";
        } else if (keyword.equals("default") || keyword.equals("")) { // default sorting
            this.keyword = "Default";
        } else {
            this.keyword = keyword;
            invalidKeyword = true;
        }
    }

    /**
     * Executes 'sortFilteredTaskList' and returns success message
     */
    @Override
    public CommandResult execute(boolean isUndo) {
        if (invalidKeyword) {
            return new CommandResult(String.format(MESSAGE_FAILURE, keyword));
        }
        model.sortFilteredTaskList(keyword);
        model.saveCurrentSortPreference(keyword);
        if (keyword.equals("Default")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_DEFAULT));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
