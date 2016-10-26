//@@author A0144919W
package seedu.tasklist.logic.commands;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.model.TaskList;
import seedu.tasklist.model.UndoInfo;
import seedu.tasklist.model.task.Task;
import seedu.tasklist.model.task.UniqueTaskList;
import seedu.tasklist.model.task.UniqueTaskList.TaskNotFoundException;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your previous undo action has been redone.";

    public static final String MESSAGE_FAILURE = "There are no changes that can be redone.";

    public static final int ADD_CMD_ID = 1;
    public static final int DEL_CMD_ID = 2;
    public static final int UPD_CMD_ID = 3;
    public static final int DONE_CMD_ID = 4;
    public static final int CLR_CMD_ID = 5;
    public static final int STR_CMD_ID = 6;
    
    private static final int CURRENT_TASK = 0;
    private static final int ORIGINAL_TASK_INDEX = 1;

    private UndoInfo undoInfo;
    
    @Override
    public CommandResult execute() {
        assert model != null;
        if((model.getRedoStack().isEmpty()))
           return new CommandResult(MESSAGE_FAILURE);
        undoInfo = model.getRedoStack().pop();
        int undoID = undoInfo.getUndoID();
        switch(undoID){
            case ADD_CMD_ID:
                redoAdd(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            case DEL_CMD_ID:
                redoDelete(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            case UPD_CMD_ID:
                redoUpdate(undoInfo.getTasks().get(CURRENT_TASK), undoInfo.getTasks().get(ORIGINAL_TASK_INDEX));
                return new CommandResult(MESSAGE_SUCCESS);
            case DONE_CMD_ID:
                redoDone(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            case CLR_CMD_ID:
                redoClear();
                return new CommandResult(MESSAGE_SUCCESS);
            case STR_CMD_ID:
                redoSetStorage();
                return new CommandResult(MESSAGE_SUCCESS);
            default:
                return new CommandResult(MESSAGE_FAILURE);
        }
    }

    private void redoSetStorage() {
    	assert model != null;
    	try{
    	   model.changeFileStorageRedo(undoInfo.getFilePath());
    	}
    	catch (IOException | ParseException | JSONException e) {
    		e.printStackTrace();
    	}
    }
    
    private void redoAdd(Task task) {
        try {
            model.addTaskRedo(task);
        } 
        catch (UniqueTaskList.DuplicateTaskException e) {
            e.printStackTrace();
        }
    }

    private void redoDelete(Task task) {
        try{
            model.deleteTaskRedo(task);
        }
        catch (TaskNotFoundException e){
            assert false: "The target task cannot be missing";
        }
    }

    private void redoUpdate(Task originalTask, Task newTask) {
        Task stubTask = new Task (newTask.getTaskDetails(), newTask.getStartTime(), newTask.getEndTime(), newTask.getPriority(), newTask.getTags(), newTask.getRecurringFrequency());
        try {
            model.updateTaskUndo(newTask, originalTask.getTaskDetails(), originalTask.getStartTime(), originalTask.getEndTime(), originalTask.getPriority(), originalTask.getTags(), originalTask.getRecurringFrequency());
            model.updateTaskUndo(originalTask, stubTask.getTaskDetails(), stubTask.getStartTime(), stubTask.getEndTime(), stubTask.getPriority(), stubTask.getTags(), originalTask.getRecurringFrequency());
            model.getUndoStack().push(undoInfo);
        } catch (IllegalValueException e) {
			e.printStackTrace();
		}
    }

    private void redoDone(Task task) {
        try {
            model.markTaskAsCompleteRedo(task);
        }
        catch (TaskNotFoundException e) {
            assert false: "The target task cannot be missing";
        }
    }

    private void redoClear() {
        model.resetDataRedo(TaskList.getEmptyTaskList());
    }
    
}
