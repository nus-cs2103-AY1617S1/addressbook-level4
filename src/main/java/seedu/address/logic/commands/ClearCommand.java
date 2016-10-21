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

    private boolean targetDoneList;

    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        targetDoneList = model.isCurrentListDoneList();
        if (targetDoneList) {
            clearedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueDoneTaskList().getInternalList());
        } else {
            clearedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueUndoneTaskList().getInternalList());
        }
        model.resetData(TaskManager.getEmptyTaskManager());
        updateHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public CommandResult undo() {
        if (targetDoneList) {
            model.addDoneTasks(clearedTasks);
        }
        else {
            model.addTasks(clearedTasks);
        }
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
