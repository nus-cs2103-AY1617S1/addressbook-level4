package seedu.address.logic.commands;

import java.io.IOException;

//@author A0126649W
public class SaveCommand extends Command {
    
    public static final String COMMAND_WORD = "save";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes file name and the saving directory.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/ToDoList2.xml";
    public static final String MESSAGE_SUCCESS = "File is successfully saved to: %1$s";
    public static final String MESSAGE_INVALID_PATH = "File path %1$s is a wrong file path";
    
    private final String filePath;
    
    public SaveCommand(String filePath){
        this.filePath = filePath;
    }
    
    @Override
    public CommandResult execute() {
        try{
            model.saveToDo(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        }catch (IOException e){
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, filePath));
        }
        
    }

}
