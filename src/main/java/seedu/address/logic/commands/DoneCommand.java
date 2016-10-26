package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Status;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
//@@author A0139339W
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
    public static final String MESSAGE_NOT_DONE_TASK_SUCCESS = "Task(s) not done: %1$s";

    private final int[] doneIndices;
    private final int[] notDoneIndices;

    public DoneCommand(int[] doneIndices, int[] notDoneIndices) {
        this.doneIndices = doneIndices;
        this.notDoneIndices = notDoneIndices;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        UnmodifiableObservableList<ReadOnlyTask> fullList = model.getUnfilteredTaskList();

        String doneMessage = changeStatus(lastShownList, fullList, doneIndices, "done");
        String notDoneMessage = changeStatus(lastShownList, fullList, notDoneIndices, "not done");
        
        return new CommandResult(doneMessage + "\n" + notDoneMessage);
    }
    
    private String changeStatus(UnmodifiableObservableList<ReadOnlyTask> lastShownList,
    		UnmodifiableObservableList<ReadOnlyTask> fullList,
    		int[] indices, String status) {
    	
    	assert status.equals("done") || status.equals("not done");
    	model.checkForOverdueTasks();
    	model.saveState();
    	
    	ArrayList<ReadOnlyTask> tasksList = new ArrayList<>();
    	Task taskChanged;
        
        for (int i=0; i<indices.length; i++) {
            if (lastShownList.size() < indices[i]) {
                model.loadPreviousState();
        		indicateAttemptToExecuteIncorrectCommand();
                return (Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX 
                		+ " for " + status + " command");
            }

            taskChanged = new Task(lastShownList.get(indices[i] - 1));
            int index = fullList.indexOf(taskChanged);
            taskChanged.setStatus(new Status(status));
            tasksList.add(taskChanged);
        	
            try {
                model.editTask(index, taskChanged);
            } catch (TaskNotFoundException pnfe) {
                model.loadPreviousState();
                assert false : "The target task cannot be missing";
            }
        }
        String successMessage = "";
        if(status.equals("done")) {
            successMessage = String.format(MESSAGE_DONE_TASK_SUCCESS, tasksList);
        } else if(status.equals("not done")) {
            successMessage =  String.format(MESSAGE_NOT_DONE_TASK_SUCCESS, tasksList);
        }
        return successMessage;
    }

}
