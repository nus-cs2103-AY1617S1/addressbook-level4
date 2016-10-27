package seedu.cmdo.logic.commands;

import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.events.ui.StorageFileChangedEvent;
import seedu.cmdo.commons.exceptions.IllegalValueException;

// @@author A0139661Y
public class StorageCommand extends Command {
	
    public static final String COMMAND_WORD = "storage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the storage folder path of CMDo.\n"
            + "Parameters: <file/path/name>"
            + "Example: " + COMMAND_WORD
            + " /home/Documents";

    public static final String MESSAGE_SUCCESS = "cmdo.xml now saves to %1$s";

    private final String filePath;

    /**
     * Created an command to change storage filepath.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * 
     * @@author A0139661Y
     */
    public StorageCommand(String filePath) {
    	this.filePath = filePath;
    }

    @Override
    public CommandResult execute() {
    	model.changeStorageFilePath(filePath);
        EventsCenter.getInstance().post(new StorageFileChangedEvent(filePath));
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }
}