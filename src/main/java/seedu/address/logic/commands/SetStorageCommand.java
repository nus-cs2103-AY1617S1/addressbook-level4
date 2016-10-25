//@@author A0143756Y
package seedu.address.logic.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.InvalidPathException;
import java.io.IOException;

/**
 * Sets task manager data storage location, according to user specifications.
 * Task manager data to be saved to user-specified folder, with user-specified file name.
 */
public class SetStorageCommand extends Command {

    public static final String COMMAND_WORD = "set-storage";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD 
    		+ ": Sets task manager data storage location, according to user specifications. "
    		+ "Task manager data to be saved to user-specified folder, with user-specified file name.\n"
    		+ "Parameters: FOLDER_FILE_PATH save-as FILE_NAME\n"
    		+ "Example: " + COMMAND_WORD + "C:\\Users\\Liang\\Desktop save-as TaskInformation\n";
    
    public static final String MESSAGE_SUCCESS = "Task manager data storage location set to: %s\n"
    		+ "Please exit and restart Amethyst for new storage settings to be registered.\n";
    
    public static final String MESSAGE_FOLDER_DOES_NOT_EXIST = "User-specified folder: %s does not exist.\n";
    
    public static final String MESSAGE_FOLDER_NOT_DIRECTORY = "User-specified folder: %s is not a folder.\n";
    
    public static final String MESSAGE_STORAGE_PREVIOUSLY_SET = "Task manager data storage location has previously been set to: %s\n";
    
    public static final String MESSAGE_FILE_WITH_IDENTICAL_NAME_EXISTS = "File with identical name %s exists in user-specified folder %s.\n"
    		+ "Program will not proceed to overwrite existing file.\n"
    		+ "Re-enter command with different file name.\n";
    
    public static final String MESSAGE_INVALID_PATH_EXCEPTION = "Invalid file path.\n";
    
    public static final String MESSAGE_IO_EXCEPTION = "Input/output operation failed/ interrupted.\n";
    
    public static final String MESSAGE_SECURITY_EXCEPTION = "Program not granted permission to access file(s).\n";
    
    private final String userSpecifiedStorageFolder; 
    
    private final String userSpecifiedStorageFileName;
        
    public SetStorageCommand(String userSpecifiedStorageFolder, String userSpecifiedStorageFileName) {
    	this.userSpecifiedStorageFolder = userSpecifiedStorageFolder;
    	this.userSpecifiedStorageFileName = userSpecifiedStorageFileName;
    }

	@Override
    public CommandResult execute() {
        assert model != null;
        
        model.saveState();
        
        try{
        	Path newStorageFolderFilePath = Paths.get(userSpecifiedStorageFolder);  //Throws InvalidPathException
        	
        	//
        	if(Files.notExists(newStorageFolderFilePath)){  //Throws SecurityException
        		return new CommandResult(String.format(MESSAGE_FOLDER_DOES_NOT_EXIST, userSpecifiedStorageFolder)); 
        	}
        	
        	if(!Files.isDirectory(newStorageFolderFilePath)){  //Throws SecurityException
        		return new CommandResult(String.format(MESSAGE_FOLDER_NOT_DIRECTORY, userSpecifiedStorageFolder)); 
        	}        	        	        	
        	
        	Path newStorageFileFilePath = newStorageFolderFilePath.resolve(userSpecifiedStorageFileName +".xml");  //Throws InvalidPathException
        	
        	Path oldStorageFileFilePath = Paths.get(model.getTaskManagerStorageFilePath());  //Throws InvalidPathException
        	
        	if(Files.isSameFile(newStorageFileFilePath, oldStorageFileFilePath)){  //Throws IOException, SecurityException
        		return new CommandResult(String.format(MESSAGE_STORAGE_PREVIOUSLY_SET, oldStorageFileFilePath.toString())); 
        	}
        	
        	if(Files.exists(newStorageFileFilePath)){  //Throws SecurityException
        		return new CommandResult(String.format(MESSAGE_FILE_WITH_IDENTICAL_NAME_EXISTS, userSpecifiedStorageFileName + ".xml", userSpecifiedStorageFolder));
        	}
        	
        	model.setStorage(newStorageFileFilePath.toFile(), oldStorageFileFilePath.toFile());
        	
        	return new CommandResult(String.format(MESSAGE_SUCCESS, model.getTaskManagerStorageFilePath()));
        	
        } catch (InvalidPathException ex){
        	model.loadPreviousState();
        	return new CommandResult(String.format(MESSAGE_INVALID_PATH_EXCEPTION));  	
        } catch (IOException ex){
        	model.loadPreviousState();
        	return new CommandResult(MESSAGE_IO_EXCEPTION);    
        } catch (SecurityException ex){
        	model.loadPreviousState();
        	return new CommandResult(MESSAGE_SECURITY_EXCEPTION);
        }
    }
}
