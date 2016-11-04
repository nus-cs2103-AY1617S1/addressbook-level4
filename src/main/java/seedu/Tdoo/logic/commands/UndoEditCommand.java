package seedu.Tdoo.logic.commands;

import java.util.EmptyStackException;

import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.model.task.ReadOnlyTask;
import seedu.Tdoo.model.task.Task;
import seedu.Tdoo.model.task.UniqueTaskList;
import seedu.Tdoo.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0144061U
/**
 * Undo the most recent command by the user.
 */
public class UndoEditCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the latest command. If there is no previous command, nothing will happen.\n" + "Example: "
            + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undone the latest task.";
    public static final String MESSAGE_UNDO_FAIL = "There was no undoable command made before.";
    public static final String INVALID_TYPE = "Invalid data type";
    public static final String INVALID_VALUE = "Invalid value";

    private String dataType;
    private int targetIndex;
    private final Task taskToAdd;
    public ReadOnlyTask taskToDelete = null;
    
    public UndoEditCommand(String dataType, ReadOnlyTask taskToAdd, ReadOnlyTask taskToDelete, int targetIndex) {
        this.dataType = dataType;
        this.taskToAdd = (Task) taskToAdd;
        this.taskToDelete = taskToDelete;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try{
            assert (taskToDelete != null);
            model.addTask(taskToAdd);
            model.deleteTask(taskToDelete, dataType);
        } catch (IllegalValueException ive) {
            return new CommandResult(INVALID_VALUE);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }        
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}