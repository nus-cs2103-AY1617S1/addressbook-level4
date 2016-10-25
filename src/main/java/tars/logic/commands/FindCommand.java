package tars.logic.commands;

import java.util.ArrayList;
import tars.model.task.TaskQuery;

/**
 * Finds and lists all tasks in address book whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds task based on the specified"
            + "keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters (Quick Search Mode): KEYWORD [MORE_KEYWORDS]...\n"
            + "Parameters (Filter Search Mode): /n NAME /dt DATETIME /p PRIORITY /t TAGS /do [or -ud] \n"
            + "Examples (Quick Serach Mode): " + COMMAND_WORD + " CS2103 projects" + "Examples (Filter Search Mode): "
            + COMMAND_WORD + " /n CS2103 projects /dt 10/09/2016 1000 to " + "20/09/2016 0100 /t school projects /do";

    private TaskQuery taskQuery = null;
    private ArrayList<String> quickSearchKeywords = null;
    private String searchKeywords = "";

    public FindCommand(TaskQuery taskQuery) {
        this.taskQuery = taskQuery;
    }

    public FindCommand(ArrayList<String> quickSearchKeywords) {
        this.quickSearchKeywords = quickSearchKeywords;
    }

    @Override
    public CommandResult execute() {
        if (taskQuery != null) {
            model.updateFilteredTaskListUsingFlags(taskQuery);
            searchKeywords = "\n" + taskQuery.toString();
        }

        if (quickSearchKeywords != null) {
            model.updateFilteredTaskListUsingQuickSearch(quickSearchKeywords);
            searchKeywords = "\nQuick Search Keywords: " + quickSearchKeywords.toString();
        }
        return new CommandResult(
                getMessageForTaskListShownSummary(model.getFilteredTaskList().size()) + searchKeywords);
    }

}
