package seedu.tasklist.logic.commands;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

//import com.google.common.io.Files;

import seedu.tasklist.MainApp;
import seedu.tasklist.commons.core.Config;
import seedu.tasklist.storage.Storage;
import seedu.tasklist.storage.StorageManager;

public class SetStorageCommand extends Command {
	public static final String COMMAND_WORD = "setstorage";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Sets the storage file path\n"
			+ "Parameters: [store][valid file path]\n"
			+ "Example: " + COMMAND_WORD + " src/main/java/seedu/tasklist/logic/commands/SetStorageCommand.java";

	public static final String MESSAGE_DONE_TASK_SUCCESS = "Changed file path to: ";
	public static final String SET_STORAGE_FAILURE = "File path not found. Please enter a valid file path.";
    protected Storage storage;
	private static String filePath;
	public SetStorageCommand(String filepath){
		this.filePath = filepath;

	}

	public CommandResult execute() throws IOException, JSONException, ParseException{
		//filePath = filePath+"/tasklist.xml";
		File targetListFile = new File(filePath);
		File defaultFilePath = new File("/Users/dheeraj/dheeraj5/data/tasklist.xml");
		Path defaultPath = defaultFilePath.toPath();
	    Config config = new Config();
		if(targetListFile.exists()){
			Path targetPath = targetListFile.toPath();
			  try {
				//Files.copy(defaultPath,targetPath);
			//	Files.createFile(targetPath);
				  Files.copy(defaultFilePath.toPath(), targetListFile.toPath(), StandardCopyOption.REPLACE_EXISTING); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			    config.setTaskListFilePath(filePath);
			   // storage = new StorageManager(config.getTaskListFilePath(), config.getUserPrefsFilePath());    
			    return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS + filePath));
		}
		else
			return new CommandResult(String.format(SET_STORAGE_FAILURE));
	}
}
