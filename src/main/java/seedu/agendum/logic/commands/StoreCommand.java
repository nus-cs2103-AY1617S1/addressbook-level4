package seedu.agendum.logic.commands;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.commons.util.StringUtil;

//@@author A0148095X
/** Allow the user to specify a folder as the data storage location **/
public class StoreCommand extends Command {
    
    public static final String COMMAND_WORD = "store";
    public static final String COMMAND_FORMAT = "store <path-to-file>";
    public static final String COMMAND_DESCRIPTION = "stores task list at specified location";
    public static final String COMMAND_EXAMPLE = "store agendum/todolist.xml";
	
    public static final String MESSAGE_SUCCESS = "New save location: %1$s";
    public static final String MESSAGE_LOCATION_DEFAULT = "Save location set to default: %1$s";

    public static final String MESSAGE_LOCATION_INACCESSIBLE = "The specified location is inaccessible; try running Agendum as administrator.";
    public static final String MESSAGE_FILE_EXISTS = "The specified file exists; would you like to use LOAD instead?";
    public static final String MESSAGE_PATH_WRONG_FORMAT = "The specified path is in the wrong format. Example: " + COMMAND_EXAMPLE;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Specify a save location. \n"
            + "Parameters: PATH_TO_FILE\n" 
            + "Example: " 
            + COMMAND_EXAMPLE;
    
    private String pathToFile;

    public StoreCommand(String location) {
        this.pathToFile = location.trim();
    }

    @Override
    public CommandResult execute() {
        assert pathToFile != null;
        
        if(pathToFile.equalsIgnoreCase("default")) { // for debug
            String defaultLocation = Config.DEFAULT_SAVE_LOCATION;
            model.changeSaveLocation(defaultLocation);
            return new CommandResult(String.format(MESSAGE_LOCATION_DEFAULT, defaultLocation));
        }

        if(isFileExists()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_FILE_EXISTS);
        }
        
        if(!isPathCorrectFormat()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_PATH_WRONG_FORMAT);            
        }

        if(!isPathAvailable()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_LOCATION_INACCESSIBLE);
        }

        model.changeSaveLocation(pathToFile);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pathToFile));
    }
    
    private boolean isPathCorrectFormat() {
        return StringUtil.isValidPathToFile(pathToFile);
    }
    
    private boolean isPathAvailable() {
        return FileUtil.isPathAvailable(pathToFile);
    }
    
    private boolean isFileExists() {
        return FileUtil.isFileExists(pathToFile);
    }
	
    public static String getName() {
        return COMMAND_WORD;
    }
	
    public static String getFormat() {
        return COMMAND_FORMAT;
    }
	
    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }
    
}
