package seedu.tasklist.logic.commands;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
		if(filePath.equals("default")){
			filePath = "/data/tasklist.xml";
		}
     	File targetListFile = new File(filePath);
     	FileReader read= new FileReader("config.json");
        JSONObject obj = (JSONObject) new JSONParser().parse(read);
    	String currentFilePath = (String) obj.get("taskListFilePath");
    	File currentTaskListPath = new File(currentFilePath);
	    Config config = new Config();
			  try {
				  Files.move(currentTaskListPath.toPath(), targetListFile.toPath(), StandardCopyOption.REPLACE_EXISTING); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			    config.setTaskListFilePath(filePath);   
			    return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS + filePath));
	}
}
