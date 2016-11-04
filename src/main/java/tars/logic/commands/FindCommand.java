package tars.logic.commands;

import java.util.ArrayList;

import tars.commons.core.EventsCenter;
import tars.commons.events.ui.ScrollToTopEvent;
import tars.commons.util.StringUtil;
import tars.model.task.TaskQuery;

/**
 * @@author A0124333U
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all tasks containing a list of keywords (i.e. AND search)."
            + "keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters (Quick Search Mode): <KEYWORD> [KEYWORD ...]\n"
            + "Parameters (Filter Search Mode): [/n NAME_KEYWORD ...] [/dt DATETIME] [/p PRIORITY] [/do] [/ud] [/t TAG_KEYWORD ...]\n"
            + "Examples (Quick Serach Mode): " + COMMAND_WORD
            + " CS2103 projects" + "Examples (Filter Search Mode): "
            + COMMAND_WORD + " /n CS2103 projects /dt 10/09/2016 1000 to "
            + "20/09/2016 0100 /t school projects /do";

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
            searchKeywords = StringUtil.STRING_NEWLINE + taskQuery.toString();
        }

        if (quickSearchKeywords != null) {
            model.updateFilteredTaskListUsingQuickSearch(quickSearchKeywords);
            searchKeywords =
                    StringUtil.STRING_NEWLINE + "Quick Search Keywords: "
                            + quickSearchKeywords.toString();
        }
        EventsCenter.getInstance().post(new ScrollToTopEvent());
        return new CommandResult(getMessageForTaskListShownSummary(
                model.getFilteredTaskList().size()) + searchKeywords);
    }

}
