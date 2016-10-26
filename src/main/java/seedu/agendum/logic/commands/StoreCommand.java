package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.commons.util.StringUtil;

//@@author A0148095X
/** Allow the user to specify a folder as the data storage location **/
public class StoreCommand extends Command {
    
    public static final String COMMAND_WORD = "store";
    public static final String COMMAND_FORMAT = "store <location>";
    public static final String COMMAND_DESCRIPTION = "stores task list at specified location";
	
    public static final String MESSAGE_SUCCESS = "New save location: %1$s";
    public static final String MESSAGE_LOCATION_DEFAULT = "Save location set to default: %1$s";

    public static final String MESSAGE_LOCATION_INVALID = "The specified location is invalid.";
    public static final String MESSAGE_FILE_EXISTS = "The specified file exists; would you like to use LOAD instead?";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Specify a save location. \n"
            + "Parameters: PATH_TO_FILE\n" 
            + "Example: " + COMMAND_WORD 
            + "agendum/todolist.xml";
    
    private String pathToFile;

    public StoreCommand(String location) {
        this.pathToFile = location.trim();
    }

    @Override
    public CommandResult execute() {
        assert pathToFile != null;
        
        if(pathToFile.equalsIgnoreCase("default")) {
            String defaultLocation = Config.DEFAULT_SAVE_LOCATION;
            model.changeSaveLocation(defaultLocation);
            return new CommandResult(String.format(MESSAGE_LOCATION_DEFAULT, defaultLocation));
        }

        if(isFileExists()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_FILE_EXISTS);
        }

        if(!isLocationValid()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_LOCATION_INVALID);
        }

        model.changeSaveLocation(pathToFile);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pathToFile));
    }
    
    private boolean isLocationValid() {
        boolean isValidPathToFile = StringUtil.isValidPathToFile(pathToFile);
        if(!isValidPathToFile) {// Don't do the more expensive check if this one fails
            return false;
        }
        boolean isPathAvailable = FileUtil.isPathAvailable(pathToFile);
        
        return isValidPathToFile && isPathAvailable;
    }
    
    private boolean isFileExists() {
        return FileUtil.isFileExists(pathToFile);
    }
	
    @Override
    public String getName() {
        return COMMAND_WORD;
    }
	
    @Override
    public String getFormat() {
        return COMMAND_FORMAT;
    }
	
    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }
    
}
