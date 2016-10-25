package seedu.agendum.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.core.UnmodifiableObservableList;
import seedu.agendum.model.task.ReadOnlyTask;
import seedu.agendum.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes task(s) identified using their last displayed indices from the task listing.
 */
public class DeleteCommand extends Command {

    // COMMAND_WORD, COMMAND_FORMAT, COMMAND_DESCRIPTION are for display in help window
    public static final String COMMAND_WORD = "delete";
    public static String COMMAND_FORMAT = "delete <index> \ndelete <index> <more-indexes>";
    public static String COMMAND_DESCRIPTION = "delete task(s) from Agendum";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tasks(s) identified by their index numbers used in the last task listing.\n"
            + "Parameters: INDEX... (must be a positive number)\n"
            + "Example: " + COMMAND_WORD + " 1 2 5-6";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task(s): %1$s";

    public ArrayList<Integer> targetIndexes;

    public ArrayList<ReadOnlyTask> tasksToDelete;

    public DeleteCommand() {}

    //@@author A0133367E
    public DeleteCommand(Set<Integer> targetIndexes) {
        this.targetIndexes = new ArrayList<Integer>(targetIndexes);
        Collections.sort(this.targetIndexes);
        this.tasksToDelete = new ArrayList<ReadOnlyTask>();
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (isAnyIndexInvalid(lastShownList)) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
 
        for (int targetIndex: targetIndexes) {
            ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);
            tasksToDelete.add(taskToDelete);
        }

        try {
            model.deleteTasks(tasksToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS,
                CommandResult.tasksToString(tasksToDelete, targetIndexes)));
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
