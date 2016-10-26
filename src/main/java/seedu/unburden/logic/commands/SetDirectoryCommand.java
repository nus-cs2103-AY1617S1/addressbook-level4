package seedu.unburden.logic.commands;

import java.io.IOException;

import seedu.unburden.commons.core.Config;
import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.commons.util.ConfigUtil;
import seedu.unburden.commons.util.FileUtil;

public class SetDirectoryCommand extends Command{
	
	public static final String COMMAND_WORD = "setdir";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": changes the save directory to the user-designated directory. \n"
			+ "Parameters: FILEPATH/FILENAME.xml or FILEPATH\\FILENAME.xml \n"
			+ "Example: " + COMMAND_WORD + " C:/Users/jim/Desktop \n"
			+ "To reset the directory to the default directory, type \"setdir reset\" ";
	
	public static final String COMMAND_RESET = "reset";

	public static final String MESSAGE_CONFIG_FILE_NOT_FOUND = "Config file not found!";
	public static final String MESSAGE_SUCCESS = "New directory: %1$s";
	public static final String MESSAGE_SAME_AS_CURRENT = "Target directory is the same as current directory";
	public static final String MESSAGE_INVALID_PATH = "Invalid directory!";
	
	
	public String currentConfigPath = Config.DEFAULT_CONFIG_FILE;
	
	public String newDirectory; 
	
	public SetDirectoryCommand(String directory) {
		this.newDirectory = directory;
	}
	
	@Override
	public CommandResult execute() {
		if (!FileUtil.isValidPath(newDirectory)) {
			return new CommandResult(MESSAGE_INVALID_PATH);
		}
		try {
			Config currentConfig = ConfigUtil.readConfig(currentConfigPath).orElse(new Config());
			String currentDirectory = currentConfig.getTaskListFilePath();
			if (currentDirectory.equals(newDirectory)) {
				indicateAttemptToExecuteIncorrectCommand(); 
				return new CommandResult(MESSAGE_SAME_AS_CURRENT);
			}
			
			currentConfig.setTaskListFilePath(newDirectory);
			ConfigUtil.saveConfig(currentConfig, currentConfigPath);
			indicateStoragePathChange(currentDirectory, newDirectory);
			return new CommandResult(String.format(MESSAGE_SUCCESS, currentConfig.getTaskListFilePath()));
			
		} catch (DataConversionException e) {
			return new CommandResult(MESSAGE_CONFIG_FILE_NOT_FOUND);
		} catch (IOException ee) {
			return new CommandResult(ee.getMessage());
		}
	}
}
