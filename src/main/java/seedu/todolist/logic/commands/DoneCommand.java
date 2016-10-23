package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todolist.ui.MainWindow;

//@@author A0138601M
/**
 * Marks a task identified using it's last displayed index from the to do list.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified using it's last displayed index from the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 \n";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: \t %1$s";
    
    public static final String MESSAGE_MARK_COMPLETED_TASK = "This task is already completed!";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = null;
        
        if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
            return new CommandResult(MESSAGE_MARK_COMPLETED_TASK);
        }
        else {
            lastShownList = model.getFilteredIncompleteTaskList();
        }

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);

        try {
            model.markTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
}
