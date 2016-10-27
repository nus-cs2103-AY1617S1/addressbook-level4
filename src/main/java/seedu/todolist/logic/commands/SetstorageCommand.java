package seedu.todolist.logic.commands;

/**
 * Sets the location of the storage file. 
 */
public class SetstorageCommand extends Command{

public static final String COMMAND_WORD = "setstorage";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Sets the location of the storage file. \n"
    		+ "Example: " + COMMAND_WORD
    		+ " C://user/documents/todolist";
    
    public static final String MESSAGE_SUCCESS = "Successfully set the storage location!";
    
    public static final String MESSAGE_Invalid_Path = "Given location path is invalid.";

    private final String filepath;
    
    public SetstorageCommand(String filepath) {
    	this.filepath = filepath;
    }
	
    @Override
	public CommandResult execute() {
		storage.setFilePath(filepath);
    	return new CommandResult(MESSAGE_SUCCESS);
	}
	

}