package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CommandUtil;
import seedu.address.model.TaskBook.TaskType;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.ui.PersonListPanel;

/**
 * Deletes a task identified using it's last displayed index from the task book.
 */
public class DeleteCommand extends Command implements Undoable {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number given in the most recent listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " A1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final String targetIndex;
    private ReadOnlyTask target; 

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
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        
        TaskType type = CommandUtil.getTaskType(targetIndex);
        int indexNum = CommandUtil.getIndex(targetIndex);
        
        if (type == TaskType.DATED) {
            target = lastDatedTaskList.get(indexNum - 1);
        }
        else if (type == TaskType.UNDATED){
            target = lastUndatedTaskList.get(indexNum - 1);
        }
        else {
            assert false : "Task type not found";
        }

        try {
            model.deleteTask(target);
            populateUndo();
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be found";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, target));
    }

    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert target != null;
        model.addUndo(COMMAND_WORD, target);
    }
}
