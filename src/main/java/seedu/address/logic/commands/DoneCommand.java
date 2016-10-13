package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/*
 * @author A0141128R
 * need to to add a task done list and need to store the task that is done
 */

public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number used in the last task listing as complete and deletes it from the list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Done task: %1$s";

    public final int targetIndex;

    public DoneCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToComplete = (Task) lastShownList.get(targetIndex - 1);

        try {
            model.doneTask(taskToComplete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToComplete.getDetail().details));
    }

}