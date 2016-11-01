package harmony.mastermind.logic.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


//@@author A0124797R
/**
 * Imports a txt file of commands to add tasks automatically
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": Reads file and add all task from file into Mastermind\n"
                                               + "Parameters: File location\n"
                                               + "Example: "
                                               + COMMAND_WORD
                                               + " Users/newUser/Desktop/file.txt";

    public static final String MESSAGE_READ_SUCCESS = "Read success on imported file";
    public static final String MESSAGE_READ_FAILURE = "Invalid file path: %1$s";
    public static final String MESSAGE_IMPORT_SUCCESS = "Import success %1$s tasks added";
    public static final String MESSAGE_IMPORT_FAILURE = "Import failure: %1$s tasks added \nInvalid lines: %2$s";
    
    private String fileToImport;
    private ArrayList<String> lstOfCmd;
    
    public ImportCommand(String filePath) {
        this.fileToImport = filePath.trim();
        lstOfCmd = new ArrayList<String>();
    }
    
    @Override
    public CommandResult execute() {
        assert fileToImport != null;
        assert lstOfCmd != null;
        
        File file = new File(fileToImport);
        try  
        {  
           String fileType = Files.probeContentType(file.toPath());  
           System.out.println(fileType);
        }  
        catch (IOException ioException)  
        {  
           System.out.println(  
                "ERROR: Unable to determine file type for " + fileToImport  
                   + " due to exception " + ioException);  
        }  

        try {
            BufferedReader br = model.importFile(fileToImport);
            
            String line;
            
            while ((line = br.readLine()) != null) {
                lstOfCmd.add(line);
            }
            
        } catch (IOException e) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_READ_FAILURE, fileToImport));
        }
        
        return new CommandResult(COMMAND_WORD , MESSAGE_READ_SUCCESS);
    }
    
    /** gets the list of commands for adding tasks */
    public ArrayList<String> getTaskToAdd() {
        return this.lstOfCmd;
    }

}
