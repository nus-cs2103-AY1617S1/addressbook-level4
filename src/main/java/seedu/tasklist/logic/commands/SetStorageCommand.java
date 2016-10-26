/* @@author A0135769N */
package seedu.tasklist.logic.commands;
import java.io.IOException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import seedu.tasklist.storage.Storage;

public class SetStorageCommand extends Command {
	public static final String COMMAND_WORD = "setstorage";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Sets the storage file path\n"
			+ "Parameters: [store][valid file path]\n"
			+ "Example: " + COMMAND_WORD + " src/main/java/seedu/tasklist/logic/commands/tasklist.xml";

	public static final String MESSAGE_SUCCESS = "Changed file path to: ";
	public static final String SET_STORAGE_FAILURE = "File path not found. Please enter a valid file path.";
    
	protected Storage storage;
	private static String filePath;
	
	public SetStorageCommand(String path){
		filePath = path;

	}

	public CommandResult execute() throws IOException, JSONException, ParseException {
		assert model != null;
		model.changeFileStorage(filePath);
		return new CommandResult(String.format(MESSAGE_SUCCESS + filePath));
	}
}
