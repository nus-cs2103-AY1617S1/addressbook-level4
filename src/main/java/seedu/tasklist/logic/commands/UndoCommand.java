package seedu.tasklist.logic.commands;

import seedu.tasklist.model.ModelManager;
import seedu.tasklist.model.UndoInfo;
import seedu.tasklist.model.task.ReadOnlyTask;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your previous action has been undone.";

    public static final String MESSAGE_FAILURE = "There are no changes that can be undone.";

    public static final int ADD_CMD_ID = 1;
    public static final int DEL_CMD_ID = 2;
    public static final int UPD_CMD_ID = 3;
    public static final int DONE_CMD_ID = 4;
    
    private static final int CURRENT_TASK = 0;
    private static final int ORIGINAL_TASK_INDEX = 1;

    private UndoInfo undoInfo;


    @Override
    public CommandResult execute() {
        assert model != null;
        
        undoInfo = ModelManager.undoStack.pop();
        ModelManager.redoStack.push(undoInfo);
        int undoID = undoInfo.getUndoID();
        switch (undoID) {
            case ADD_CMD_ID:
                undoAdd(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            case DEL_CMD_ID:
                undoDelete(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);    
            case UPD_CMD_ID:
                undoUpdate(undoInfo.getTasks().get(CURRENT_TASK), undoInfo.getTasks().get(ORIGINAL_TASK_INDEX));
                return new CommandResult(MESSAGE_SUCCESS);
            case DONE_CMD_ID:
                undoDone(undoInfo.getTasks().get(CURRENT_TASK));
                return new CommandResult(MESSAGE_SUCCESS);
            default:
                return new CommandResult(MESSAGE_FAILURE);
        }
    }
    
    private void undoAdd(ReadOnlyTask task){
        
    }
    
    private void undoDelete(ReadOnlyTask task){
        
    }

    private void undoUpdate(ReadOnlyTask newTask, ReadOnlyTask originalTask){
        
    }

    private void undoDone(ReadOnlyTask task){
        
    }

}
