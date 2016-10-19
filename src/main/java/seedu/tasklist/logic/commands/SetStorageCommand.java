package seedu.tasklist.logic.commands;
import java.io.File;

import seedu.tasklist.MainApp;
import seedu.tasklist.commons.core.Config;
import seedu.tasklist.storage.Storage;
import seedu.tasklist.storage.StorageManager;

public class SetStorageCommand extends Command {
	public static final String COMMAND_WORD = "setstorage";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Sets the storage file path\n"
			+ "Parameters: [store][valid file path]\n"
			+ "Example: " + COMMAND_WORD + " src/main/java/seedu/tasklist/logic/commands/";

	public static final String MESSAGE_DONE_TASK_SUCCESS = "Changed file path to: ";
	public static final String SET_STORAGE_FAILURE = "File path not found. Please enter a valid file path.";
    protected Storage storage;
	private static String filePath;
	public SetStorageCommand(String filepath){
		this.filePath = filepath;

	}

	public CommandResult execute(){
		File taskListFile = new File(filePath);
	    Config config = new Config();
		if(taskListFile.exists()){
			    config.setTaskListFilePath(filePath);
			    storage = new StorageManager(config.getTaskListFilePath(), config.getUserPrefsFilePath());
			    return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS + filePath));
		}
		else
			return new CommandResult(String.format(SET_STORAGE_FAILURE));
	}
}
