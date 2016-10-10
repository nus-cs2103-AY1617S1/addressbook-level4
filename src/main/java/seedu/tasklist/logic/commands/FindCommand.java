package seedu.tasklist.logic.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
import seedu.tasklist.model.task.UniqueTaskList.PersonNotFoundException;

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

    public static final String MESSAGE_FIND_TASK_FAILURE = "No such task was found.";
    private final String keywords;
    
    public FindCommand(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        Set<String> taskNameSet = new HashSet<String>();
        taskNameSet.add(keywords);
        model.updateFilteredTaskList(taskNameSet);
        UnmodifiableObservableList<ReadOnlyTask> matchingTasks = model.getFilteredPersonList();
        
        // No tasks match string
        if (matchingTasks.isEmpty()){
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_FIND_TASK_FAILURE));
        }
        
        // One or more tasks match string
        else {
            Set<String> words = new HashSet<String>();
            words.add(keywords);
            model.updateFilteredTaskList(words);
            return new CommandResult(String.format(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())));
        }
    }

}
