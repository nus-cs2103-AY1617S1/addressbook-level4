package seedu.agendum.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Unmark task(s) identified using their last displayed indices in the task listing.
 */
public class UnmarkCommand extends Command {

 // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "unmark";
    public static String COMMAND_FORMAT = "unmark <index> \nunmark <index> <more-indexes>";
    public static String COMMAND_DESCRIPTION = "mark task(s) as uncomplete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Ununmarks the tasks(s) identified by their index numbers used in the last task listing.\n"
            + "Parameters: INDEX... (must be a positive number)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5-6";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Ununmarked Task(s): %1$s";

    public ArrayList<Integer> targetIndexes;

    public ArrayList<ReadOnlyTask> tasksToUnmark;

    public UnmarkCommand() {}

    //@@author A0133367E    
    public UnmarkCommand(Set<Integer> targetIndexes) {
        this.targetIndexes = new ArrayList<Integer>(targetIndexes);
        Collections.sort(this.targetIndexes);
        this.tasksToUnmark = new ArrayList<ReadOnlyTask>();
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (isAnyIndexInvalid(lastShownList)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
 
        for (int targetIndex: targetIndexes) {
            ReadOnlyTask taskToUnmark = lastShownList.get(targetIndex - 1);
            tasksToUnmark.add(taskToUnmark);
        }
        
        try {
            model.unmarkTasks(tasksToUnmark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS,
                CommandResult.tasksToString(tasksToUnmark, targetIndexes)));
    }

    private boolean isAnyIndexInvalid(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return targetIndexes.stream().anyMatch(index -> index > lastShownList.size());
    }

    //@@author
    @Override
    public String getName() {
        return COMMAND_WORD;
    }

    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
}
