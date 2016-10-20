package seedu.tasklist.logic.commands;

import static seedu.tasklist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.util.regex.Matcher;

import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.events.storage.ChangePathEvent;
import seedu.tasklist.commons.events.ui.JumpToListRequestEvent;
import seedu.tasklist.commons.exceptions.IllegalValueException;


/**
 * Change File path
 */

public class StorageCommand extends Command {

	private String filepath;

	public static final String COMMAND_WORD = "storage";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the filepath.\n"
			+ "Parameters: file directory\n" + "Example: " + COMMAND_WORD + " C:/tasklist.xml";
	public static final String MESSAGE_CHANGE_PATH_SUCCESS = "File Path updated at ";
	public static final String MESSAGE_FILE_PATH_NOT_EXIST = "File Path not exist!";

	public StorageCommand() {
	}

	public StorageCommand(String filepath) {
		this.filepath = filepath;
	}
	
	  /**
     * Convenience constructor using raw values.
     *
     * display MESSAGE_FILE_PATH_NOT_EXIST if filepath not exist
     */
	@Override
	public CommandResult execute() {
		File file = new File(filepath);
		if (file.getAbsolutePath()==null) {
			return new CommandResult(MESSAGE_FILE_PATH_NOT_EXIST);
		}
		
		EventsCenter.getInstance().post(new ChangePathEvent(filepath));
		model.updateFilePathChange();
		return new CommandResult(MESSAGE_CHANGE_PATH_SUCCESS + filepath);
	}
	
	 /**
     * Parses arguments in the context of the storage command.
     *
     * @param args full command args string
     * @return the prepared command
     */
	@Override
	public Command prepare(String args) {
		final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
		if (!matcher.matches()) {
			return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
		}

		String filepath = args.trim();
		return new StorageCommand(filepath);

	}

}
