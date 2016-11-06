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
     * Parses the keyword given by user based on first character of the input
     * 
     * @param keyword
     *            keyword given by user to sort tasks by
     */
    public SortCommand(String keyword) {
        if ("d".equals(keyword) || "deadline".equals(keyword) || "dead".equals(keyword)) { // deadline
            this.keyword = "Deadline";
        } else if ("s".equals(keyword) || "starttime".equals(keyword) || "start".equals(keyword)) { // start time
            this.keyword = "Start Time";
        } else if ("e".equals(keyword) || "endtime".equals(keyword) || "end".equals(keyword)) { // end time
            this.keyword = "End Time";
        } else if ("c".equals(keyword) || "completed".equals(keyword) || "done".equals(keyword)) { // done status
            this.keyword = "Completed";
        } else if ("f".equals(keyword) || "favorite".equals(keyword) || "favourite".equals(keyword)) { // favorite status
            this.keyword = "Favorite";
        } else if ("o".equals(keyword) || "overdue".equals(keyword)) { // overdue status
            this.keyword = "Overdue";
        } else if ("n".equals(keyword) || "name".equals(keyword)) { // name
            this.keyword = "Name";
        } else if ("default".equals(keyword) || "".equals(keyword)) { // default sorting
            this.keyword = "Default";
        } else {
            this.keyword = keyword;
            invalidKeyword = true;
        }
    }

    /**
     * Executes 'sortFilteredTaskList' and returns a message to inform if it was successful or has failed.
     */
    @Override
    public CommandResult execute(boolean isUndo) {
        if (invalidKeyword) {
            return new CommandResult(String.format(MESSAGE_FAILURE, keyword));
        }
        model.sortFilteredTaskList(keyword);
        model.saveCurrentSortPreference(keyword);
        if ("Default".equals(keyword)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_DEFAULT));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

}
