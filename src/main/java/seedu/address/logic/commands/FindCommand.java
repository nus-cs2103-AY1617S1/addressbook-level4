package seedu.address.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.address.model.task.ReadOnlyTask;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private String keywords;

    public FindCommand(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
/*        
        List<ReadOnlyTask> matchingTasks = model.getFilteredTaskListFromTaskName(keywords);
        Set<String> keywordList = model.getKeywordsFromList(matchingTasks);
        model.updateFilteredPersonList(keywordList);
        //model.updateFilteredPersonList(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
*/
        return null;
    }

}
