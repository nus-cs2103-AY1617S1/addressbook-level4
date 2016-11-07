package jym.manager.logic.commands;

import jym.manager.commons.core.Messages;
import jym.manager.commons.core.UnmodifiableObservableList;
import jym.manager.model.task.ReadOnlyTask;
import jym.manager.model.task.Task;
import jym.manager.model.task.UniqueTaskList.TaskNotFoundException;
import jym.manager.ui.MainWindow;

/**
 * Complete a task identified using it's last displayed index from the task manager.
 */

//@@author a0153617e
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed Task!";
    public static final String MESSAGE_MARK_COMPLETED_TASK = "This task is already completed!";
    
    public static final int MULTIPLE_MARK_OFFSET = 1;
    
//    public final int[] targetIndexes;
    private final int targetIndex;
    
    public CompleteCommand(int tgtindex) {
        this.targetIndex = tgtindex;
    }


    @Override
    public CommandResult execute() {

    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredIncompleteTaskList();

//    	if (model.getCurrentTab().equals(MainWindow.TAB_TASK_COMPLETE)) {
//            return new CommandResult(MESSAGE_MARK_COMPLETED_TASK);
//        }
//        else {
//            lastShownList = model.getFilteredIncompleteTaskList();
//        }

//        if (!isValidIndexes(lastShownList, targetIndexes)) {
//            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//        }
//        
//        ReadOnlyTask[] tasksToMark = new ReadOnlyTask[targetIndexes.length];        
//        for (int i = 0; i < targetIndexes.length; i++) {
//            tasksToMark[i] = lastShownList.get(targetIndexes[i] - MULTIPLE_MARK_OFFSET);
//        }
    	 if (lastShownList.size() < targetIndex) {
             indicateAttemptToExecuteIncorrectCommand();
             return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
         }
        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            model.completeTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(MESSAGE_COMPLETE_TASK_SUCCESS);
    }
    
    private boolean isValidIndexes(UnmodifiableObservableList<ReadOnlyTask> lastShownList, int[] targetIndex) {
        for (int index : targetIndex) {
            if (lastShownList.size() < index) {
                indicateAttemptToExecuteIncorrectCommand();
                return false;
            }
        }
        return true;
    }

}
