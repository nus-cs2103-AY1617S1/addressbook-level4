package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;

public class SaveCommand extends Command {
	
	public static final String COMMAND_WORD = "save";
	
	public static final String MESSAGE_SUCCESS = "File is saved successfully.";
	
	public static final String MESSAGE_INCORRECT_FILE_PATH = "The file path entered is not correct!\n"
                                                           	+"Path components should be seperated by slash!\n"
															+ "Example:"
															+ COMMAND_WORD
															+ "./data/TPTM.xml";
	
	private String taskManagerFilePath;
        
    public SaveCommand(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

	@Override
	public CommandResult execute() {
        assert config != null;
        
               
        if (!(this.taskManagerFilePath.contains(".xml") && this.taskManagerFilePath.contains("/") )) {
        	return new CommandResult( MESSAGE_INCORRECT_FILE_PATH);
        }
        
        try {
			storage.saveAddressBook(model.getAddressBook(), this.taskManagerFilePath);
		} catch (IOException e) {
			return new CommandResult(MESSAGE_INCORRECT_FILE_PATH);
		}
        
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
