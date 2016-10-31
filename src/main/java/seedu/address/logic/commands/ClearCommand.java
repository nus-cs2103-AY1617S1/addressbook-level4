package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.TaskManager;
import seedu.address.model.item.Task;
import seedu.address.model.item.UniqueTaskList;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears task manager permanently.\n\t"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "clear";

    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid the clear command! Tasks have been restored!";


    private ObservableList<Task> clearedTasks;

    private boolean viewingDoneList;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        
        // record the target list if this is a new action (and not a redo action)
        if (!isRedoAction()) {
            viewingDoneList = model.isCurrentListDoneList();
        }
        
        // save the list in clearedTasks for undo/redo purposes
        // clear the appropriate list
        if (viewingDoneList) {
            clearedTasks = model.getTaskManager().getUniqueDoneTaskList().getInternalList();
            model.setTaskManagerDoneList(FXCollections.observableArrayList());
        } else {
            
            clearedTasks = model.getTaskManager().getUniqueUndoneTaskList().getInternalList();
            model.setTaskManagerUndoneList(FXCollections.observableArrayList());

        }
       
        // update the history with this command
        updateHistory();
          
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0093960X
    @Override
    public CommandResult undo() {
        assert model != null && clearedTasks != null;
           
        // attempt to undo the clear by adding back the list of tasks that was cleared
        // add back to the list the user was viewing when clear was executed
        if (viewingDoneList) {
            model.setTaskManagerDoneList(clearedTasks);

        }
        else {
            model.setTaskManagerUndoneList(clearedTasks);

        }
        
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
    
    //@@author 
}
