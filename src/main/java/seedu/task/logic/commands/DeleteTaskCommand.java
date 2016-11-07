package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.model.item.ReadOnlyTask;
import seedu.task.model.item.Task;
import seedu.task.model.item.UniqueTaskList.TaskNotFoundException;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.core.Messages;
import seedu.task.commons.core.UnmodifiableObservableList;

/**
 * @@author A0121608N
 * Deletes a Task identified using it's last displayed index from the taskbook.
 * 
 */
public class DeleteTaskCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    private ReadOnlyTask taskToDelete;
    
    private final Logger logger = LogsCenter.getLogger(DeleteTaskCommand.class);
    
    public DeleteTaskCommand(int targetIndex) {
        this.lastShownListIndex = targetIndex;
    }


	public DeleteTaskCommand(Task taskToDelete) {
        this.taskToDelete = taskToDelete;
    }


    @Override
    public CommandResult execute() {
        assert model != null;
        
        if(taskToDelete == null){
            UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        
            if (outOfBounds(lastShownList.size())) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
    
            taskToDelete = lastShownList.get(lastShownListIndex - 1);
        }
        
        logger.info("-------[Executing DeleteTaskCommand] " + this.toString() );
        
        try {
            model.deleteTask(taskToDelete);
        } catch (TaskNotFoundException tnfe) {
            assert false : "The target task cannot be missing";
        }
        
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

    private boolean outOfBounds(int listSize){
        return listSize < lastShownListIndex || lastShownListIndex < 1;
    }
	
	@Override
	public CommandResult undo() {
		AddTaskCommand reverseCommand = new AddTaskCommand(taskToDelete);
		reverseCommand.setData(model);
		
		return reverseCommand.execute();
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.taskToDelete.getAsText();
	}

}
