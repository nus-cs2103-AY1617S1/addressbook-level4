package seedu.address.logic.commands;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.item.Task;

//@@author A0093960X
/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    private final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Clears the current view of the task manager.\n\t" + "Example: " + COMMAND_WORD;

    public static final String TOOL_TIP = "clear";
    public static final String MESSAGE_SUCCESS_UNDONE_LIST = "Task Manager undone list has been cleared!";
    public static final String MESSAGE_SUCCESS_DONE_LIST = "Task Manager done list has been cleared!";
    public static final String MESSAGE_UNDO_SUCCESS_UNDONE_LIST = "Undid the clear command!"
            + " Cleared tasks from the undone list have been restored!";
    public static final String MESSAGE_UNDO_SUCCESS_DONE_LIST = "Undid the clear command!"
            + " Cleared tasks from the undone list have been restored!";

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

    @Override
    public CommandResult undo() {
        assert model != null && clearedTasks != null;

        if (isTargetDoneList) {
            revertDoneListBeforeClear();
            return new CommandResult(MESSAGE_UNDO_SUCCESS_DONE_LIST);
        } else {
            revertUndoneListBeforeClear();
            return new CommandResult(MESSAGE_UNDO_SUCCESS_UNDONE_LIST);
        }

    }

    /**
     * Sets the undone list back to the original list before clearing.
     */
    private void revertUndoneListBeforeClear() {
        logger.info("Undoing the clear command by reverting the undone list "
                + "back to the old list before it was cleared");
        model.setTaskManagerUndoneList(clearedTasks);
    }

    /**
     * Sets the done list back to the original list before clearing.
     */
    private void revertDoneListBeforeClear() {
        logger.info("Undoing the clear command by reverting the done list "
                + "back to the old list before it was cleared");
        model.setTaskManagerDoneList(clearedTasks);
    }

    /**
     * Updates the target list of this clear command to the appropriate list.
     */
    private void updateTargetList() {
        logger.info("Clear command is executed as a new action and not part of a redo action."
                + " Saving the current list view as target list.");
        isTargetDoneList = model.isCurrentListDoneList();
    }

    /**
     * Saves the current undone list, and then clears the undone list to an
     * empty list.
     */
    private void saveAndClearUndoneList() {
        logger.info("Attempting to save and clear the undone list.");
        clearedTasks = model.getTaskManagerUndoneList();
        model.clearTaskManagerUndoneList();
    }

    /**
     * Saves the current done list, and then clears the done list to an empty
     * list.
     */
    private void saveAndClearDoneList() {
        logger.info("Attempting to save and clear the done list.");
        clearedTasks = model.getTaskManagerDoneList();
        model.clearTaskManagerDoneList();
    }

}
