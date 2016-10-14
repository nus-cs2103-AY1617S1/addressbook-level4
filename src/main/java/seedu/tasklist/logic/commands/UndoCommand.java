package seedu.tasklist.logic.commands;

import seedu.tasklist.model.UndoInfo;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your previous action has been undone.";

    public static final String MESSAGE_FAILURE = "Unable to undo. You have not made any changes yet.";

    public static final int ADD_CMD_ID = 1;
    public static final int DEL_CMD_ID = 2;
    public static final int UPD_CMD_ID = 3;
    public static final int DONE_CMD_ID = 4;

    private UndoInfo undoInfo;

    public UndoCommand(UndoInfo undoInfo) {
        this.undoInfo = undoInfo;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        int undoID = undoInfo.getUndoID();
        switch (undoID) {
            case 1:
                
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                
        }
        return null;
    }

}
