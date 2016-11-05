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
    public static final String MESSAGE_SUCCESS_UNDONE_LIST = "Task Manager undone list has been cleared!";
    public static final String MESSAGE_SUCCESS_DONE_LIST = "Task Manager done list has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS = "Undid the clear command! Cleared tasks from the %1$s list have been restored!";

    private ObservableList<Task> clearedTasks;
    private boolean isTargetDoneList;

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        if (!isRedoAction) {
            updateTargetList();
        }

        updateHistory();
        if (isTargetDoneList) {
            saveAndClearDoneList();
            return new CommandResult(MESSAGE_SUCCESS_DONE_LIST);
        } else {
            saveAndClearUndoneList();
            return new CommandResult(MESSAGE_SUCCESS_UNDONE_LIST);
        }

    }

    private void updateTargetList() {
        isTargetDoneList = model.isCurrentListDoneList();
    }

    private void saveAndClearUndoneList() {
        clearedTasks = model.getTaskManagerUndoneList();
        model.clearTaskManagerUndoneList();
    }

    private void saveAndClearDoneList() {
        clearedTasks = model.getTaskManagerDoneList();
        model.clearTaskManagerDoneList();
    }

    @Override
    public CommandResult undo() {
        assert model != null && clearedTasks != null;

        if (isTargetDoneList) {
            model.setTaskManagerDoneList(clearedTasks);
            return new CommandResult(MESSAGE_SUCCESS_DONE_LIST);
        } else {
            model.setTaskManagerUndoneList(clearedTasks);
            return new CommandResult(MESSAGE_SUCCESS_UNDONE_LIST);
        }

    }

}
