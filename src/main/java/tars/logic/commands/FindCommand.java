package tars.logic.commands;

import java.util.ArrayList;
import tars.model.task.TaskQuery;

/**
 * Finds and lists all tasks in address book whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " CS2103 projects";

    private TaskQuery taskQuery = null;
    private ArrayList<String> lazySearchKeywords = null;
    private String searchKeywords = "";

    public FindCommand(TaskQuery taskQuery) {
        this.taskQuery = taskQuery;
    }

    public FindCommand(ArrayList<String> lazySearchKeywords) {
        this.lazySearchKeywords = lazySearchKeywords;
    }
    
    
    @Override
    public CommandResult execute() {
        if (taskQuery != null) {
            model.updateFilteredTaskListUsingFlags(taskQuery);
            searchKeywords = "\n" + taskQuery.toString();
        }

        if (lazySearchKeywords != null) {
            model.updateFilteredTaskListUsingLazySearch(lazySearchKeywords);
            searchKeywords = "\nQuick Search Keywords: " + lazySearchKeywords.toString();
        }
        return new CommandResult(
                getMessageForTaskListShownSummary(model.getFilteredTaskList().size()) + searchKeywords);
    }

}
