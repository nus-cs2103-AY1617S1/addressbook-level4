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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears task manager permanently.\n\t"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "clear";

    public static final String MESSAGE_SUCCESS = "Task Manager has been cleared!";
    
    public static final String MESSAGE_UNDO_SUCCESS = "Undid the clear command! Tasks have been restored!";


    private List<Task> clearedTasks;


    public ClearCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null;
        clearedTasks = new ArrayList<Task>(model.getTaskManager().getUniqueUndoneTaskList().getInternalList());
        model.resetData(TaskManager.getEmptyTaskManager());
        updateHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public CommandResult undo() {
        model.addTasks(clearedTasks);
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
