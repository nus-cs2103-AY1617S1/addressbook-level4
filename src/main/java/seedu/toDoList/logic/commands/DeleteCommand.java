package seedu.toDoList.logic.commands;

import static seedu.toDoList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toDoList.commons.core.UnmodifiableObservableList;
import seedu.toDoList.model.task.ReadOnlyTask;
import seedu.toDoList.model.task.TaskList.TaskNotFoundException;

//@@author A0142325R

/**
 * Deletes a task identified using its last displayed index or name from the
 * toDoList.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number or specific name used in the most recent listing.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME\n" + "Example: " + COMMAND_WORD
            + " 1 or horror night";

    public static final String MESSAGE_DELETE_SAME_NAME = "Please select the item identified "
            + "by the index number.\n" + "Parameters: INDEX(must be a positive integer)\n" + "Example: " + COMMAND_WORD
            + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event: %1$s";
    public static final String MESSAGE_DELETE_NOT_FOUND = "Item to delete is not found";
    public static final String MESSAGE_DELETE_INVALID_INDEX = "The index provided is invalid";
    // one or more keywords separated by whitespace
    private static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)");

    public final int targetIndex;
    public final String name;

    /**
     * construct DeleteCommand by index. Precondition: targetIndex is a valid
     * non-negative integer.
     * 
     * @param targetIndex.
     */
    public DeleteCommand(int targetIndex) {
        assert targetIndex >= 0;
        this.targetIndex = targetIndex;
        this.name = null;
    }

    /**
     * construct DeleteCommand by name. Precondition: name is not null.
     * 
     * @param name.
     * @param k.
     */
    public DeleteCommand(String name) {
        assert name != null;
        this.name = name;
        this.targetIndex = Integer.MIN_VALUE;
    }

    @Override
    public CommandResult execute() {
        ReadOnlyTask taskToDelete = null;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (targetIndex != Integer.MIN_VALUE) {
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(MESSAGE_DELETE_INVALID_INDEX);
            }
            taskToDelete = prepareDeleteTaskbyIndex(lastShownList);
        } else {
            return prepareDeleteTaskWithName();
        }
        return deleteTask(taskToDelete);

    }

    /**
     * return taskToBeDeleted found by targetIndex.
     * 
     * @param lastShownList.
     * @return task to be deleted.
     */
    private ReadOnlyTask prepareDeleteTaskbyIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(targetIndex - 1);
    }

    /**
     * shown all task names with one or more occurrences of the input
     * parameters.
     * 
     * @return commandResult.
     */
    private CommandResult prepareDeleteTaskWithName() {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
        if ( ! matcher.matches()) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        model.updateFilteredTaskList(keywordSet);
        if (model.getFilteredTaskList().size() == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_DELETE_NOT_FOUND);
        } else {
            return new CommandResult(MESSAGE_DELETE_SAME_NAME);
        }
    }

    /**
     * delete the task specified
     * 
     * @param taskToDelete
     * @return commandResult
     */
    private CommandResult deleteTask(ReadOnlyTask taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        String message = String.format(getDeleteSuccessMessage(taskToDelete), taskToDelete);
        model.saveState(message);
        return new CommandResult(message);
    }

    /**
     * return the correct delete success message depending on the whether it is
     * task or event
     * 
     * @param TaskToDelete
     * @return String
     */
    private static String getDeleteSuccessMessage(ReadOnlyTask TaskToDelete) {
        if (TaskToDelete.isEvent()) {
            return MESSAGE_DELETE_EVENT_SUCCESS;
        } else {
            return MESSAGE_DELETE_TASK_SUCCESS;
        }
    }

}
