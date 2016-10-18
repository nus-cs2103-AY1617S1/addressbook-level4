package seedu.address.logic.commands.taskcommands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.StorageChangedEvent;
import seedu.address.logic.commands.CommandResult;

/**
 * Sets the application storage location to another folder
 */
public class SetStorageCommand extends TaskCommand {

	public static final String COMMAND_WORD = "setstorage";

	public static final String MESSAGE_USAGE = COMMAND_WORD 
			+ ": Sets the folder to be used for storage\n" 
			+ "Parameters: FOLDERPATH\n"
			+ "Example: " + COMMAND_WORD + " C:/Users/Bob/Desktop/";

	public static final String MESSAGE_SET_STORAGE_SUCCESS = "Storage location succesfully set to %1$s.";
	public static final String MESSAGE_SET_STORAGE_FAILURE = "Cannot set storage location to \"%1$s\"!";

	String storageLocation;

	public SetStorageCommand(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	@Override
	public CommandResult execute() {

		if (!isDirectory(storageLocation)) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(String.format(MESSAGE_SET_STORAGE_FAILURE, storageLocation));
		}
			
		EventsCenter.getInstance().post(new StorageChangedEvent(storageLocation));
		return new CommandResult(String.format(MESSAGE_SET_STORAGE_SUCCESS, storageLocation));
	}
	
	/*z
	 * Checks if a provided folder path from the user is a valid directory
	 * Should be a directory, writable and readable
	 */
	private boolean isDirectory(String folderpath) {
		if (folderpath == null || folderpath.isEmpty()) {
			return false;
		}
		
		try {
			Path path = Paths.get(folderpath);
			return Files.isDirectory(path) && Files.isWritable(path) && Files.isReadable(path);
		} catch (InvalidPathException ipe) {
			return false;
		} catch (SecurityException sece) {
			return false;
		}
		
	}
	

}
