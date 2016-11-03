package jym.manager.logic.commands;

//@@author a0153617e

/**
 * Sets the location of the storage file. 
 */
public class SaveToCommand extends Command{

public static final String COMMAND_WORD = "saveto";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Sets the location of the storage file. \n"
    		+ "Example: " + COMMAND_WORD
    		+ " C://Users/User/Documents/JYM";
    
    public static final String MESSAGE_SUCCESS = "Successfully set the storage location!";
    
    public static final String MESSAGE_Invalid_Path = "Given location path is invalid.";

    private final String filepath;
    
    public SaveToCommand(String filepath) {
    	this.filepath = filepath;
    }
	
    @Override
	public CommandResult execute() {
		storage.setFilePath(filepath);
		config.setTaskManagerFilePath(filepath + "/taskmanager.xml");

		return new CommandResult(MESSAGE_SUCCESS);
    	
	}
	

}