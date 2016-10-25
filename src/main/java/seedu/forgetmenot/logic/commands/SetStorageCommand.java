package seedu.forgetmenot.logic.commands;

import java.io.File;

import seedu.forgetmenot.commons.core.EventsCenter;
import seedu.forgetmenot.commons.events.storage.StorageLocationChangedEvent;

public class SetStorageCommand extends Command {
	
	public static final String COMMAND_WORD = "setstorage";
	
    public static final String MESSAGE_SUCCESS = "Changed storage location to %1$s. "
    		+ "Make a change to ForgetMeNot to save your data in the new location";
    public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Sets the storage file path\n"
			+ "Parameters: [valid file path]\n"
			+ "Example: " + COMMAND_WORD + " data/taskmanager.xml";
    public static final String MESSAGE_WRONG_EXTENSION =  "File must have a .xml extension";
    public static final String MESSAGE_CANNOT_CREATE = "Unable to create file, please check path provided";
    public static final String MESSAGE_ALREADY_EXISTS_SUCCESS = MESSAGE_SUCCESS
    			+ ".\nWarning - file already exists, please check that the old file does not contain any important information."
    			+ "\nIf you wish to undo this action, change storage location BEFORE making any changes to the data and BEFORE exiting ForgetMeNot";
    public static final String MESSAGE_ALREADY_EXISTS_NO_OVERWRITE = "File already exists, and I don't have permission to overwrite it";
    public static final String MESSAGE_NO_PERMISSION = "Please provide an accessible location";
    
    private String filePath;
    
    public SetStorageCommand(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public CommandResult execute() {
		if (!hasXmlExtension(filePath)) {
			return new CommandResult(MESSAGE_WRONG_EXTENSION);
		}
		
		String feedbackToUser;
		if (fileAlreadyExists(filePath)) {
			if (canOverWriteExisting(filePath)) {
				feedbackToUser = String.format(MESSAGE_ALREADY_EXISTS_SUCCESS, filePath);
			} else {
				return new CommandResult(MESSAGE_ALREADY_EXISTS_NO_OVERWRITE);
			}
		} else {
			if (canWriteToFile(filePath)) {
				feedbackToUser = String.format(MESSAGE_SUCCESS, filePath);
			} else {
				return new CommandResult(MESSAGE_NO_PERMISSION);
			}
		}
		
		EventsCenter.getInstance().post(new StorageLocationChangedEvent(filePath));
		return new CommandResult(feedbackToUser);
	}
	
	private boolean hasXmlExtension(String filePath) {
		return filePath.endsWith(".xml") && !filePath.equals("");
	}
	
	private boolean fileAlreadyExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	
	private boolean canOverWriteExisting(String filePath) {
		File file = new File(filePath).getParentFile();
		return file.canWrite();
	}
	
	private boolean canWriteToFile(String filePath) {
		File file = new File(filePath).getParentFile();
		if(file == null)
			return false;
		return file.canWrite();
	}
}