package seedu.malitio.logic.commands;

import java.util.Arrays;
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
 * 
 */
public class DeleteCommand extends Command {

    //@@author a0126633j
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index used in the last task listing.\n"
            + "Parameters: INDEX \n" + "Example: " + COMMAND_WORD + " D1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted %1$s";

   private static final String[] TYPES_OF_TASKS = {"f","d", "e"}; 
   private static final String FLOATING_TASK_KEYWORD = "f";
   private static final String DEADLINE_KEYWORD = "d";
   private static final String EVENT_KEYWORD = "e";

   private static final int MINIMUM_INDEX_IN_LIST = 1;
   
    private final int targetIndex;
    private final String taskType;

    public DeleteCommand(String taskType, int targetIndex) {
        this.taskType = taskType;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        assert(Arrays.asList(TYPES_OF_TASKS).contains(taskType));

        int sizeOfList = 0;
        
        switch (taskType) {
        case FLOATING_TASK_KEYWORD:
            UnmodifiableObservableList<ReadOnlyFloatingTask> lastShownFloatingTaskList = model.getFilteredFloatingTaskList();
            sizeOfList = lastShownFloatingTaskList.size();
            break;
        case DEADLINE_KEYWORD:
            UnmodifiableObservableList<ReadOnlyDeadline> lastShownDeadlineList = model.getFilteredDeadlineList();
            sizeOfList = lastShownDeadlineList.size();
            break;
        case EVENT_KEYWORD:
            UnmodifiableObservableList<ReadOnlyEvent> lastShownEventList = model.getFilteredEventList();
            sizeOfList = lastShownEventList.size();
        }

        if (sizeOfList < targetIndex || targetIndex < MINIMUM_INDEX_IN_LIST) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        switch (taskType) {
        case FLOATING_TASK_KEYWORD:
            ReadOnlyFloatingTask taskToDelete = model.getFilteredFloatingTaskList().get(targetIndex - 1);
            executeDelete(taskToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));

        case DEADLINE_KEYWORD:
            ReadOnlyDeadline deadlineToDelete = model.getFilteredDeadlineList().get(targetIndex - 1);
            executeDelete(deadlineToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deadlineToDelete));

        default:
            assert(taskType.equals(EVENT_KEYWORD));
            ReadOnlyEvent eventToDelete = model.getFilteredEventList().get(targetIndex - 1);
            executeDelete(eventToDelete);
            model.getFuture().clear();
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, eventToDelete));
        }
    }
    
    /**
     * Deletes a task from the model
     */
    private void executeDelete(Object taskToDelete) {
        try {
            model.deleteTask(taskToDelete);
        } catch (FloatingTaskNotFoundException pnfe) {
            assert false : "The target floating task cannot be missing";
        } catch (DeadlineNotFoundException e) {
            assert false : "The target deadline cannot be missing";
        } catch (EventNotFoundException e) {
            assert false : "The target event cannot be missing";
        }
    }
}