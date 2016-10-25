package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.storage.XmlTaskListStorage;

//@@author A0147967J
/**
 * Changes the file path of the file and exports all existing data to the specified file.
 */
public class ChangeDirectoryCommand extends Command{
	
	public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the directory for the tasklist."
            + "Parameters: FILE_PATH\n"
            + "Example: " + COMMAND_WORD
            + " D:\t.xml";

    public static final String MESSAGE_SUCCESS = "Alert: This operation is irreversible.\nFile path successfully changed to : %1$s";
    public static final String MESSAGE_IO_ERROR = "Error when saving/reading file...";
    public static final String MESSAGE_CONVENSION_ERROR = "Wrong file type/Invalid file path detected.";

    private final String filePath;
    
    public ChangeDirectoryCommand(String filePath){
    	this.filePath = filePath;
    }

	@Override
	public CommandResult execute() {
		try{
			if(!filePath.endsWith(".xml"))
				throw new DataConversionException(null);
			XmlTaskListStorage newFile = new XmlTaskListStorage(filePath);
			newFile.saveTaskList(model.getTaskMaster(), filePath);
			model.changeDirectory(filePath);
			Config config = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE).get();
			config.setTaskListFilePath(filePath);
			ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
			urManager.resetQueue();
			return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
		}catch (DataConversionException dce){
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(MESSAGE_CONVENSION_ERROR);
		}catch (IOException ioe){
			indicateAttemptToExecuteFailedCommand();
			return new CommandResult(MESSAGE_IO_ERROR);
		}
	}

}
