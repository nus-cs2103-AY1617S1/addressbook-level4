package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.model.item.Task;

//@@author A0093960X
/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears the current view of the task manager.\n\t"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "clear";
    public static final String MESSAGE_SUCCESS = "Task Manager %1$s list has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid the clear command! Cleared tasks from the %1$s list have been restored!";

    private ObservableList<Task> clearedTasks;
    private boolean isTargetDoneList;

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        if (!isRedoAction) {
            isTargetDoneList = model.isCurrentListDoneList();
        }

        if (isTargetDoneList) {
            clearedTasks = model.getTaskManager().getUniqueDoneTaskList().getInternalList();
            model.clearTaskManagerDoneList();
        } else {
            clearedTasks = model.getTaskManager().getUniqueUndoneTaskList().getInternalList();
            model.clearTaskManagerUndoneList();
        }

        updateHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, getClearedListName()));
    }

    @Override
    public CommandResult undo() {
        assert model != null && clearedTasks != null;

        if (isTargetDoneList) {
            model.setTaskManagerDoneList(clearedTasks);
        } else {
            model.setTaskManagerUndoneList(clearedTasks);
        }

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, getClearedListName()));
    }

    /**
     * Returns the list that are restored as part of the clear command.
     * 
     * @return "done" if tasks are restored from the done list, "undone" if
     *         tasks are restored from the undone list.
     */
    private String getClearedListName() {
        return (isTargetDoneList) ? "done" : "undone";
    }

}
