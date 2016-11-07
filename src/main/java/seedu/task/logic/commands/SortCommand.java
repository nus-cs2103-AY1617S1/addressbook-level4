package seedu.task.logic.commands;

import seedu.task.logic.RollBackCommand;

// @@author A0147944U
/**
 * Lists all tasks in the task manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String COMMAND_WORD_ALT = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts tasks according to specified parameters.\n"
            + "If parameter is not given, tasks will be sorted by a default preset.\n" + "Parameters: PARAMETER\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks with order: %1$s";

    public static final String MESSAGE_SUCCESS_DEFAULT = "Listed all tasks with order: Default Preset\n"
            + "Tasks are sorted according to these criteria in the order:\n"
            + "Incomplete tasks, Floating tasks, Older tasks,\n" + "lastly Name in ascending order.";

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
        switch (keyword) {
        case "d":
        case "deadline":
        case "dead":
            this.keyword = "Deadline";
            break;
        case "s":
        case "starttime":
        case "start":
            this.keyword = "Start Time";
            break;
        case "e":
        case "endtime":
        case "end":
            this.keyword = "End Time";
            break;
        case "c":
        case "completed":
        case "done":
            this.keyword = "Completed";
            break;
        case "f":
        case "favorite":
        case "favourite": // Because British
        case "fav":
            this.keyword = "Favorite";
            break;
        case "o":
        case "overdue":
        case "over":
            this.keyword = "Overdue";
            break;
        case "n":
        case "name":
        case "title":
            this.keyword = "Name";
            break;
        case "default":
        case "":
        case "standard":
        case "original":
            this.keyword = "Default";
            break;
        default:
            this.keyword = keyword;
            invalidKeyword = true;
            break;
        }
    }

    /**
     * Executes 'sortFilteredTaskList' and returns a message to inform if it was
     * successful or has failed.
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
        if (!isUndo) {
            history.getUndoList().add(new RollBackCommand(COMMAND_WORD, null, null, keyword));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, keyword));
    }

}
