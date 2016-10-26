package seedu.tasklist.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.tasklist.commons.core.UnmodifiableObservableList;
import seedu.tasklist.model.task.ReadOnlyTask;
//@@author A0144919W
/**
 * Finds and lists all tasks in the to-do list whose task details contain any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " buy eggs laundry";

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
        UnmodifiableObservableList<ReadOnlyTask> matchingTasks = model.getFilteredTaskList();
        
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
            return new CommandResult(String.format(getMessageForTaskListShownSummary(model.getFilteredTaskList().size())));
        }
    }

}
