package seedu.malitio.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.core.UnmodifiableObservableList;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
import seedu.malitio.model.task.UniqueDeadlineList.DeadlineNotFoundException;
import seedu.malitio.model.task.UniqueEventList.EventNotFoundException;
import seedu.malitio.model.task.UniqueFloatingTaskList.FloatingTaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from Malitio.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

//    private static final Set<String> TYPES_OF_TASKS = new HashSet<String>(Arrays.asList("f", "d", "e"));

    private final int targetIndex;
    private final char taskType;

    public DeleteCommand(char taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        if (!(taskType == 'f' || taskType == 'd' ||taskType == 'e')) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        int sizeOfList = 0;

        switch (taskType) {
        case 'f':
            UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownFloatingTaskList = model
                    .getFilteredFloatingTaskList();
            sizeOfList = lastShownFloatingTaskList.size();
            break;
        case 'd':
            UnmodifiableObservableList<ReadOnlyDeadline> lastShownDeadlineList = model.getFilteredDeadlineList();
            sizeOfList = lastShownDeadlineList.size();
            break;
        default:
            UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
            sizeOfList = lastShownEventList.size();
        }

        if (sizeOfList < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        switch (taskType) {
        case 'f':
            ReadOnlyFloatingTask taskToDelete = model.getFilteredFloatingTaskList().get(targetIndex - 1);
            executeDelete(taskToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));

        case 'd':
            ReadOnlyDeadline deadlineToDelete = model.getFilteredDeadlineList().get(targetIndex - 1);
            executeDelete(deadlineToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deadlineToDelete));

        default:
            ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(targetIndex - 1);
            executeDelete(eventToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, eventToDelete));
        }
    }

    private void executeDelete(ReadOnlyFloatingTask taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target floating task cannot be missing";
        }
    }

    private void executeDelete(ReadOnlyDeadline taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (DeadlineNotFoundException pnfe) {
            assert false : "The target deadline cannot be missing";
        }
    }

    private void executeDelete(ReadOnlyEvent taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (EventNotFoundException pnfe) {
            assert false : "The target event cannot be missing";
        }
    }
}
