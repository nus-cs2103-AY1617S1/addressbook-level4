package seedu.tasklist.logic.commands;

import seedu.tasklist.model.ModelManager;
import seedu.tasklist.model.UndoInfo;

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
        if((ModelManager.redoStack.isEmpty()))
           return new CommandResult(MESSAGE_FAILURE);
        undoInfo = ModelManager.redoStack.pop();
        int undoID = undoInfo.getUndoID();
        switch(undoID){
            case ADD_CMD_ID:
                redoAdd();
                return new CommandResult(MESSAGE_SUCCESS);
            case DEL_CMD_ID:
                redoDelete();
                return new CommandResult(MESSAGE_SUCCESS);
            case UPD_CMD_ID:
                redoUpdate();
                return new CommandResult(MESSAGE_SUCCESS);
            case DONE_CMD_ID:
                redoDone();
                return new CommandResult(MESSAGE_SUCCESS);
            case CLR_CMD_ID:
                redoClear();
                return new CommandResult(MESSAGE_SUCCESS);
            case STR_CMD_ID:
                //redoSetStorage();
                return new CommandResult(MESSAGE_SUCCESS);
            default:
                return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
