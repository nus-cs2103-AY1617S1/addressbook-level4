package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0142325R

/**
 * Mark the specified task or event as done.
 */

public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the specified task or event as done \n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " horror night";
    public static final String MARK_DONE_SUCCESS = "Marked as done: %1$s";
    public static final String MULTIPLE_TASK_SATISFY_KEYWORD = "Please select the Task identified "
            + "by the index number.\n" + "Parameters: INDEX(must be a positive integer)\n" + "Example: " + COMMAND_WORD
            + " 1";
    public static final String TASK_NOT_FOUND = "Task not found: %1$s";

    private final Set<String> keywords;
    public final int targetIndex;

    public DoneCommand(Set<String> keywords) {
        this.keywords = keywords;
        targetIndex = -1;
    }

    public DoneCommand(int indexToMark) {
        keywords = null;
        targetIndex = indexToMark;
    }

    @Override
    public CommandResult execute() {
        if (keywords != null && targetIndex == -1) {
            return markAsDoneByName();
        } else if (keywords == null && targetIndex != -1) {
            return markAsDoneByIndex();
        } else {
            return new CommandResult(DoneCommand.MESSAGE_USAGE);
        }
    }

    /**
     * mark a task done by name
     * 
     * @return commandResult
     */

    private CommandResult markAsDoneByName() {
        model.updateFilteredTaskList(keywords);
        if (model.getFilteredTaskList().size() == 0) {
            return new CommandResult(TASK_NOT_FOUND);
        } else {
            return new CommandResult(MULTIPLE_TASK_SATISFY_KEYWORD);
        }
    }

    /**
     * mark a task done by index
     * 
     * @return commandResult
     */

    private CommandResult markAsDoneByIndex() {

        ReadOnlyTask taskToMark = null;
        ;
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        taskToMark = lastShownList.get(targetIndex - 1);
        return markTaskDone(taskToMark);
    }

    /**
     * mark a task in the parameter input as done
     * 
     * @param task
     * @return
     */

    private CommandResult markTaskDone(ReadOnlyTask task) {
        model.markTask(task);
        String message = String.format(MARK_DONE_SUCCESS, task);
        model.saveState(message);
        return new CommandResult(message);
    }

}
