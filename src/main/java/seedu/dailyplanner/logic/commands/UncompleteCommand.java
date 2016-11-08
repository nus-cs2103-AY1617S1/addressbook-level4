package seedu.dailyplanner.logic.commands;

import seedu.dailyplanner.commons.core.EventsCenter;
import seedu.dailyplanner.commons.core.Messages;
import seedu.dailyplanner.commons.core.UnmodifiableObservableList;
import seedu.dailyplanner.commons.events.ui.JumpToListRequestEvent;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Uncompletes a task identified using it's last displayed index from the daily planner.
 */
public class UncompleteCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "uncomplete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks a completed task identified by the index number used in the last task listing as incomplete.\n"
            + "Format: uncomplete [INDEX] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNCOMPLETED_TASK_SUCCESS = "Uncompleted Task: %1$s";

    public UncompleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
  //@@author A0146749N
    @Override
    public CommandResult execute() {

	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        ReadOnlyTask taskToUncomplete = lastShownList.get(targetIndex - 1);

        try {
            model.getHistory().stackCompleteInstruction(taskToUncomplete);
            model.markTaskAsIncomplete(taskToUncomplete);
            model.updatePinBoard();
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_UNCOMPLETED_TASK_SUCCESS, taskToUncomplete));
    }

}
