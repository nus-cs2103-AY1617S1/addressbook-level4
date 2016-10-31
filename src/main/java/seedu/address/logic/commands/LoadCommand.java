package seedu.address.logic.commands;

import java.io.IOException;

//@@author A0126649W
public class LoadCommand extends Command {
    
    public static final String COMMAND_WORD = "load";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens file from specific directory.\n"
            + "Parameters: FILE_PATH/FILE_NAME.xml\n"
            + "Example: " + COMMAND_WORD + " data/todolist1.xml";
    public static final String MESSAGE_SUCCESS = "File is successfully opened from: %1$s";
    public static final String MESSAGE_INVALID_PATH = "File path %1$s is a wrong file path";
    
    private final String filePath;
    
    public LoadCommand(String filePath){
        this.filePath = filePath;
    }
    
    @Override
    public CommandResult execute() {
        try{
            model.loadToDo(filePath);
            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
        }catch (IOException e){
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, filePath));
        }
        
    }

}
