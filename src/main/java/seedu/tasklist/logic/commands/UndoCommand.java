package seedu.tasklist.logic.commands;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Your previous action has been undone.";

    public static final String MESSAGE_FAILURE = "Unable to undo. You have not made any changes yet.";

    public static final int ADD_CMD_ID = 1;
    public static final int DEL_CMD_ID = 2;
    public static final int UPD_CMD_ID = 3;
    public static final int DONE_CMD_ID = 4;
    
    
    
    @Override
    public CommandResult execute() {


         
        
        return null;
    }

}
