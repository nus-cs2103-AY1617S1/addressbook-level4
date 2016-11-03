package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.util.ConfigUtil;

//@@author A0144202Y
public class LoadCommand extends Command {
	
	public static final String COMMAND_WORD = "load";
	
	public static final String MESSAGE_SUCCESS = "File is loaded successfully.";
	
	public static final String MESSAGE_INCORRECT_FILE_PATH = "The file path entered is not correct!\n"
															+"Path components should be seperated by slash!\n"
															+ "Example:"
															+ COMMAND_WORD
															+ "./data/TPTM.xml";
	
	public static final String MESSAGE_INCORRECT_FILE = "The file does not exist";

    
	private String taskManagerFilePath;
        
    public LoadCommand(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

	@Override
	public CommandResult execute() {
        assert config != null;
  
              
        File file = new File(this.taskManagerFilePath);
        
        if (!(this.taskManagerFilePath.contains(".xml") && this.taskManagerFilePath.contains("/") )) {
        	return new CommandResult(MESSAGE_INCORRECT_FILE_PATH);
        }
        else if (!file.exists()) {
        	return new CommandResult(MESSAGE_INCORRECT_FILE);
        }
        
        config.setAddressBookFilePath(this.taskManagerFilePath);
        
        try {
        	 ConfigUtil.saveConfig(config, config.getDefaultConfigFile());
		} catch (IOException e) {
			return new CommandResult(MESSAGE_INCORRECT_FILE_PATH);
		}
        
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
       
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
