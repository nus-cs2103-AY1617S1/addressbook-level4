package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Set the tasks identified as done using it's last displayed index from the task manager.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Set the task identified by the index number used in the last task listing as done.\n"
            + "Parameters: INDEX (positive integer) [MORE_INDICES] ... \n"
            + "Example: " + COMMAND_WORD + " 1 3";
 
    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task(s) done: %1$s";

    public final int[] targetIndices;

    public DoneCommand(int[] targetIndices) {
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        ArrayList<ReadOnlyTask> tasksDoneList = new ArrayList<>();
        Task taskDone;
        
        for (int i=0; i<targetIndices.length; i++) {
        	if (lastShownList.size() < targetIndices[i]) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            taskDone = new Task(lastShownList.get(targetIndices[i] - 1));
            taskDone.setStatus(new Status("done"));
            tasksDoneList.add(taskDone);
        	
            try {
                model.editTask(targetIndices[i], taskDone);
            } catch (TaskNotFoundException pnfe) {
                assert false : "The target task cannot be missing";
            }
        }
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, tasksDoneList));
    }

}
