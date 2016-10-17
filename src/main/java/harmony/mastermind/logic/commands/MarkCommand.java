package harmony.mastermind.logic.commands;

import harmony.mastermind.commons.core.Messages;
import harmony.mastermind.model.task.ArchiveTaskList;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.UniqueTaskList;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;
import harmony.mastermind.model.task.UniqueTaskList.TaskNotFoundException;
import javafx.collections.ObservableList;

//@@author A0124797R
public class MarkCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": mark a task as done "
                                               + "Parameters: INDEX (must be a positive integer)\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " 1";

    public static final String COMMAND_SUMMARY = "Marking a task as done:"
                                                 + "\n"
                                                 + COMMAND_WORD
                                                 + " INDEX";

    public static final String MESSAGE_SUCCESS = "%1$s has been archived";
    public static final String MESSAGE_MARKED_TASK = "%1$s is already marked";

    public static final String MESSAGE_UNDO_SUCCESS = "[Undo Mark Command] %1$s has been unmarked";

    public final int targetIndex;

    public Task taskToMark;
    public String currentTab;

    public MarkCommand(int targetIndex, String currentTab) {
        this.targetIndex = targetIndex;
        this.currentTab = currentTab;
    }

    @Override
    public CommandResult execute() {
        ObservableList<Task> lastShownList = model.getListToMark(currentTab);

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        taskToMark = lastShownList.get(targetIndex - 1);

        if (taskToMark.isMarked()) {
            return new CommandResult(String.format(MESSAGE_MARKED_TASK, taskToMark));
        }

        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }

        model.pushToUndoHistory(this);

        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark));
    }

    @Override
    // @@author A0138862W
    public CommandResult undo() {
        try {
            // remove the task that's previously added.
            model.unmarkTask(taskToMark);

            return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, taskToMark));
        } catch (DuplicateTaskException e) {
            return new CommandResult(String.format(UnmarkCommand.MESSAGE_DUPLICATE_UNMARK_TASK, taskToMark));
        } catch (ArchiveTaskList.TaskNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_IN_MASTERMIND);
        }
    }

}
