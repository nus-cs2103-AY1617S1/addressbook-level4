package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CommandUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139024M
/**
 * Deletes a task identified using it's last displayed index from the task book.
 */
public class DeleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index given in the most recent listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " A1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final String targetIndex;
    private ReadOnlyTask toDelete; 

    public DeleteCommand(String targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastDatedTaskList = model.getFilteredDatedTaskList();
        UnmodifiableObservableList<ReadOnlyTask> lastUndatedTaskList = model.getFilteredUndatedTaskList();

        if (!CommandUtil.isValidIndex(targetIndex, lastUndatedTaskList.size(), 
                lastDatedTaskList.size())){
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        
        toDelete = CommandUtil.getTaskFromCorrectList(targetIndex, lastDatedTaskList, lastUndatedTaskList);

        try {
            model.deleteTask(toDelete);
            populateUndo();
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be found";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, toDelete));
    }

    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert toDelete != null;
        model.addUndo(COMMAND_WORD, toDelete);
    }
}
