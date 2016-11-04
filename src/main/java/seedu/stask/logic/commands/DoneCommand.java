package seedu.stask.logic.commands;

import seedu.stask.commons.core.Messages;
import seedu.stask.commons.core.UnmodifiableObservableList;
import seedu.stask.commons.util.CommandUtil;
import seedu.stask.model.task.ReadOnlyTask;
import seedu.stask.model.task.Status;
import seedu.stask.model.task.UniqueTaskList.TaskNotFoundException;

//@@author A0139145E
/**
 * Sets as completed a task identified using it's last displayed index from the task book.
 */
public class DoneCommand extends Command implements Undoable{

    public final String targetIndex;
    public ReadOnlyTask toComplete;

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets as completed the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " A1";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Completed Task: %1$s";
    public static final String MESSAGE_TASK_ALREADY_DONE = "Task is already completed.";

    public DoneCommand(String targetIndex) {
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
        
        toComplete = CommandUtil.getTaskFromCorrectList(targetIndex, lastDatedTaskList, lastUndatedTaskList);

        // Task already completed
        if (toComplete.getStatus().equals(new Status(Status.State.DONE))){
            return new CommandResult(String.format(MESSAGE_TASK_ALREADY_DONE));
        }
        else {
            try {
                model.completeTask(toComplete);
                populateUndo();
            } catch (TaskNotFoundException tnfe) {
                assert false : "The target task cannot be found";
            }
            return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, toComplete));
            
        }

    }

    @Override
    public void populateUndo(){
        assert COMMAND_WORD != null;
        assert toComplete != null;
        model.addUndo(COMMAND_WORD, toComplete);
        model.clearRedo();
    } 
    
}
//@@author
