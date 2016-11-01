package seedu.address.logic.commands;

import java.nio.file.Path;
import javafx.util.Pair;

import java.nio.file.InvalidPathException;
import java.io.IOException;

//@@author A0143756Y
/**
 * Sets alias for an existing command.
 * Command alias and corresponding command phrase will be saved to XML file, "aliasbook.xml" in ./data folder.
 */
public class AddAliasCommand extends Command {

    public static final String COMMAND_WORD = "add-alias";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
    		+ ": Sets alias for an existing command.\n"
    		+ "Parameters: 'COMMAND_ALIAS' = 'COMMAND_PHRASE'\n"
    		+ "Example: " + COMMAND_WORD + " 'add-dl' = 'add deadline'\n";
    
    public static final String MESSAGE_SUCCESS = "Command alias '%s' sucessfully set for command phrase '%s'.\n";

//    public static final String MESSAGE_INVALID_PATH_EXCEPTION = "Invalid file path.\n";
//    
//    public static final String MESSAGE_IO_EXCEPTION = "Input/output operation failed/ interrupted.\n";
//    
//    public static final String MESSAGE_SECURITY_EXCEPTION = "Program not granted permission to access file(s).\n";
    
    private final String commandAlias; 
    
    private final String commandPhrase;
    
    public AddAliasCommand(String commandAlias, String commandPhrase) {
    	this.commandAlias = commandAlias;
    	this.commandPhrase = commandPhrase;
    }

	@Override
    public CommandResult execute() {
        assert model != null;
        
        model.saveState();
        
        try{
        	model.addAlias(commandAlias, commandPhrase);
        	
        	return new CommandResult(String.format(MESSAGE_SUCCESS, commandAlias, commandPhrase));
        	
//        } catch (InvalidPathException ex){
//        	model.loadPreviousState();
//        	return new CommandResult(String.format(MESSAGE_INVALID_PATH_EXCEPTION));  	
//        } catch (IOException ex){
//        	model.loadPreviousState();
//        	return new CommandResult(MESSAGE_IO_EXCEPTION);    
//        } catch (SecurityException ex){
//        	model.loadPreviousState();
//        	return new CommandResult(MESSAGE_SECURITY_EXCEPTION);
//        } catch (IllegalArgumentException ex){
//        	model.loadPreviousState();
//        	return new CommandResult(ex.getMessage());
//        }
    }
}
