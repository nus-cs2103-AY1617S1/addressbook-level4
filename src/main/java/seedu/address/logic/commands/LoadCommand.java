package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.util.ConfigUtil;

public class LoadCommand extends Command {
	
	public static final String COMMAND_WORD = "load";
	
	public static final String MESSAGE_SUCCESS = "File is loaded successfully.";
	
	public static final String MESSAGE_INCORRECT_FILE_PATH = "The file path entered is not correct!";

    private String taskManagerFilePath;
        
    public LoadCommand(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

	@Override
	public CommandResult execute() {
        assert config != null;
  
        
        config.setAddressBookFilePath(this.taskManagerFilePath);
        
        try {
        	 ConfigUtil.saveConfig(config, config.DEFAULT_CONFIG_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
