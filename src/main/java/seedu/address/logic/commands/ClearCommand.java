package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.TaskManager;
import seedu.address.model.item.Task;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    
    public static final String TOOL_TIP = "clear";

    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid the clear command! Tasks have been restored!";


    private List<Task> clearedTasks;

    private boolean viewingDoneList;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
        // record the target list if this is a new action (and not a redo action)
        if (!checkIfRedoAction()) {
            viewingDoneList = model.isCurrentListDoneList();
        }
        
        // save the list in clearedTasks for undo/redo purposes
        // clear the appropriate list
        if (viewingDoneList) {
            clearedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueDoneTaskList().getInternalList());
            model.resetDoneData(TaskManager.getEmptyTaskManager());
        } else {
            clearedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueUndoneTaskList().getInternalList());
            model.resetData(TaskManager.getEmptyTaskManager());
        }
       
        // update the history with this command
        updateHistory();
          
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public CommandResult undo() {
        assert model != null && clearedTasks != null;
           
        // attempt to undo the clear by adding back the list of tasks that was cleared
        // add back to the list the user was viewing when clear was executed
        if (viewingDoneList) {
            model.addDoneTasks(clearedTasks);
        }
        else {
            model.addTasks(clearedTasks);
        }
        
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
