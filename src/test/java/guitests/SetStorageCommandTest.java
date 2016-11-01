package guitests;

import seedu.address.commons.core.Config;
import seedu.address.logic.commands.SetStorageCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertTrue;

//@@author A0143756Y
public class SetStorageCommandTest extends TaskManagerGuiTest {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void setup() {
		try{
			File testFile = testFolder.newFile("taskmanager.xml"); //Throws IOException		
			Config testConfig = new Config();
			testConfig.setTaskManagerFilePath(testFile.getPath());
		}
		catch (IOException ex){
			System.out.println("IOException thrown in the midst of testFile creation in setUp() in SetStorageCommandTest.");
		}
	}
	
	@Test
    public void setStorage() {
    	
    	//Valid folder file path and valid file name
    
		String folderFilePath = testFolder.getRoot().getPath();
		String fileName = "taskmanagertest";
		runSetStorageCommand(folderFilePath, fileName);
    	assertSetStorageCommandSuccess(folderFilePath, fileName);
    	
    	//Storage location has previously been set
    
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_STORAGE_PREVIOUSLY_SET);
    	
    	//Invalid folder file path
    	
    	folderFilePath = "invalidFolderFilePath";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_INVALID_PATH_EXCEPTION);
    	
    	//Folder specified by user does not exist
    		
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_FOLDER_DOES_NOT_EXIST);
    	
    	//Folder file path given does not navigate to a folder/ directory
    	
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_FOLDER_NOT_DIRECTORY);
    	
    	//Invalid file name
    	
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_INVALID_PATH_EXCEPTION);
    	
    	//File with identical name exists in folder

    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_FILE_WITH_IDENTICAL_NAME_EXISTS);
    }

    private void assertSetStorageCommandSuccess(String folderFilePath, String fileName) {
    	//
        assertTrue(Files.exists(Paths.get(folderFilePath).resolve(fileName + ".xml")));
        
        
    }
    
    private void runSetStorageCommand(String folderFilePath, String fileName){
    	assert folderFilePath!= null;
    	assert fileName!= null;
    	
    	String setStorageCommand = "set-storage" + folderFilePath + "save-as" + fileName;
    	commandBox.runCommand(setStorageCommand);
    }
}
