package seedu.task.logic.commands;

public class ChangePathCommand extends UndoableCommand{
    
    public static final String COMMAND_WORD = "change-to";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes save/load location for the TaskManager "
            + "Parameters: NEW FILE PATH\n"
            + "Example: " + COMMAND_WORD
            + "taskmanager.xml";
    
    public static final String MESSAGE_SUCCESS = "File path successfully changed: %1$s";
    public static final String MESSAGE_ROLLBACK_SUCCESS = "Path change reverted: %1$s";
    public static final String MESSAGE_DUPLICATE_PATH = "This is the same path as the one being used.";
    
    private final String newFilePath;
    
    public ChangePathCommand(String newFilePath) {
        this.newFilePath = newFilePath;
    }
    
    

    @Override
    public CommandResult rollback() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }
    

}
