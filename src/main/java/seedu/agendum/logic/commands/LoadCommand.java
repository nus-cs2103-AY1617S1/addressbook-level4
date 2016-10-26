package seedu.agendum.logic.commands;

import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.commons.util.StringUtil;
import seedu.agendum.commons.util.XmlUtil;

//@@author A0148095X
/** Allow the user to load a file in the correct todolist format **/
public class LoadCommand extends Command {
    
    public static final String COMMAND_WORD = "load";
    public static final String COMMAND_FORMAT = "load <location>";
    public static final String COMMAND_DESCRIPTION = "loads task list from specified location";
    
    public static final String MESSAGE_SUCCESS = "Data successfully loaded from: %1$s";
    public static final String MESSAGE_PATH_INVALID = "The specified path to file is invalid: %1$s";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "The specified file does not exist: %1$s";
    public static final String MESSAGE_FILE_WRONG_FORMAT = "The specified file is in the wrong format: %1$s";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Specify a file to load from. \n"
            + "Parameters: PATH_TO_FILE\n" 
            + "Example: " + COMMAND_WORD 
            + "agendum/todolist.xml";
    
    private String pathToFile;

    public LoadCommand(String pathToFile) {
        this.pathToFile = pathToFile.trim();
    }

    @Override
    public CommandResult execute() {
        assert pathToFile != null;

        if(!isValidPathToFile()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_PATH_INVALID, pathToFile));
        }
        
        if(!isFileExists()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_FILE_DOES_NOT_EXIST, pathToFile));            
        }
        
        if(!isFileCorrectFormat()) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_FILE_WRONG_FORMAT, pathToFile));            
        }

        model.loadFromLocation(pathToFile);
        return new CommandResult(String.format(MESSAGE_SUCCESS, pathToFile));
    }
    
    private boolean isFileCorrectFormat() {
        return XmlUtil.isFileCorrectFormat(pathToFile);
    }

    private boolean isValidPathToFile() {
        return StringUtil.isValidPathToFile(pathToFile);
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
