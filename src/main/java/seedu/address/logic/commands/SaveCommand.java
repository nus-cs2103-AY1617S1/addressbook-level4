package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.util.ConfigUtil;

public class SaveCommand extends Command {
	
	public static final String COMMAND_WORD = "save";
	
	public static final String MESSAGE_SUCCESS = "File is saved successfully.";

    private String taskManagerFilePath;
    
    public SaveCommand(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

	@Override
	public CommandResult execute() {
        assert config != null;
  
        
       
        
        try {
        	 ConfigUtil.saveConfig(config, config.DEFAULT_CONFIG_FILE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        config.setAddressBookFilePath(this.taskManagerFilePath);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
