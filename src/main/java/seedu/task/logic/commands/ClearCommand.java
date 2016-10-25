package seedu.task.logic.commands;

import seedu.task.commons.core.UnmodifiableObservableList;
import seedu.task.logic.RollBackCommand;
import seedu.task.model.TaskManager;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

/**
 * Clears the task manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task manager has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute(boolean isUndo) {
        assert model != null;
        
        
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        for(int i = 0; i < lastShownList.size(); i++){
        ReadOnlyTask taskToDelete = lastShownList.get(i);
        if(isUndo == false){
            Task task = new Task(taskToDelete.getName(),taskToDelete.getStartTime(),taskToDelete.getEndTime(),taskToDelete.getDeadline(),taskToDelete.getTags(),taskToDelete.getStatus());
            history.getUndoList().add(new RollBackCommand("clear" , task, null));
        }
        }
        model.resetData(TaskManager.getEmptyTaskManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
