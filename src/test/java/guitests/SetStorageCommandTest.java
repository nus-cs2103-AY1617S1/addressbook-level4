package guitests;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.logic.commands.SetStorageCommand;
import seedu.address.model.TaskManager;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableTaskManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

//@@author A0143756Y
public class SetStorageCommandTest extends TaskManagerGuiTest {
	
	private File testFile;
	private File testConfigJSONFile;
	private Config testConfig;
	
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Before
	public void setupStorage() throws IOException {
		System.out.println("preCommandBox: " + commandBox);
		try {
			//Creates new test file in the test folder with file name "taskmanager.xml"
			testFile = testFolder.newFile("taskmanager.xml"); //Throws IOException		
			
			//Creates new JSON configuration file, "testConfig.json"
			testConfigJSONFile = testFolder.newFile("testConfig.json");
			
			//Creates new test task manager, already populated with typical test tasks
			TaskManager testTaskManager = td.getTypicalTaskManager();
			
			//Saves test task manager data to "taskmanager.xml"
			XmlSerializableTaskManager testXmlSerializableTaskManager = new XmlSerializableTaskManager(testTaskManager); //Throws FileNotFoundException (extends IOException)
			XmlFileStorage.saveDataToFile(testFile, testXmlSerializableTaskManager); 
			
			//Creates new Config instance, testConfig 
			testConfig = new Config();
			
			//Updates taskManagerFilePath attribute in testConfig to reflect "taskmanager.xml" file path
			testConfig.setTaskManagerFilePath(testFile.getPath());
			
			//Updates configFilePath attribute in testConfig to reflect "testConfig.json" file path
			testConfig.setConfigFilePath(testConfigJSONFile.getPath());
			
			//Serializes Config instance, testConfig to JSON file, "testConfig.json"
			ConfigUtil.saveConfig(testConfig, testConfig.getConfigFilePath());
			
		} catch (IOException ex) {
			System.out.println("IOException thrown in the midst of testFile creation in setUp() in SetStorageCommandTest.");
		}
	}
	
	@Test
    public void setStorage() throws IOException, DataConversionException {
    	
    	//Valid folder file path and valid file name
    
		String folderFilePath = testFolder.getRoot().getPath();
		String fileName = "taskmanagerdata";
		String filePath = folderFilePath + "\\" + fileName + ".xml";
		runSetStorageCommand(folderFilePath, fileName);
    	assertSetStorageCommandSuccess(folderFilePath, fileName); //Throws IOException, DataConversionException
    	
    	//Storage location has previously been set
    
    	//folderFilePath = testFolder.getRoot().getPath();
    	//fileName = "taskmanagerdata";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_STORAGE_PREVIOUSLY_SET, filePath));
    	
    	//Invalid folder file path
    	
    	folderFilePath = "<>:\"/\\|?*";
    	fileName = "taskmanagerdatainfo";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_INVALID_PATH_EXCEPTION);
    	
    	//Folder specified by user does not exist
    	
    	folderFilePath = testFolder.getRoot().getPath().concat("\\nonExistentFolder");
    	//fileName = "taskmanagerdatainfo";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_FOLDER_DOES_NOT_EXIST, folderFilePath));
    	
    	//Folder file path given does not navigate to a folder/ directory
    	
    	folderFilePath = testFolder.getRoot().getPath().concat("\\taskmanager.xml");
    	//fileName = "taskmanagerdatainfo";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_FOLDER_NOT_DIRECTORY, folderFilePath));
    	
    	//Invalid file name
    	
    	folderFilePath = testFolder.getRoot().getPath();
    	fileName = "<>:\"/\\|?*";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(SetStorageCommand.MESSAGE_INVALID_PATH_EXCEPTION);
    	
    	//File with identical name exists in folder
    	
    	//folderFilePath = testFolder.getRoot().getPath();
    	fileName = "taskmanager";
    	runSetStorageCommand(folderFilePath, fileName);
    	assertResultMessage(String.format(SetStorageCommand.MESSAGE_FILE_WITH_IDENTICAL_NAME_EXISTS, fileName.concat(".xml"), folderFilePath));
    }

    private void assertSetStorageCommandSuccess(String folderFilePath, String fileName) throws IOException, DataConversionException {
    	try {
    		System.out.println("testConfig: " + testConfig.getTaskManagerFilePath());
    		Path newTaskManagerDataStorageFilePath = Paths.get(folderFilePath).resolve(fileName + ".xml");
    		
    		//Checks that new file with user-specified file name exists in user-specified data storage folder
            assertTrue(Files.exists(newTaskManagerDataStorageFilePath));
            
            //Checks that new task manager data storage file is identical to previous task manager data storage file
           
            byte[] newTaskManagerDataStorageFileContent = Files.readAllBytes(newTaskManagerDataStorageFilePath);
            byte[] oldTaskManagerDataStorageFileContent = Files.readAllBytes(testFile.toPath());
            
            assertTrue(Arrays.equals(newTaskManagerDataStorageFileContent, oldTaskManagerDataStorageFileContent));
            
            //Checks that taskManagerFilePath attribute in testConfig has been updated to reflect new task manager data storage file path
//            File newTaskManagerDataStorageFile = newTaskManagerDataStorageFilePath.toFile();
//            System.out.println("testConfig: " + testConfig.getTaskManagerFilePath());
//            System.out.println("tmdsfp: " + newTaskManagerDataStorageFile.getCanonicalPath());
//            assertEquals(testConfig.getTaskManagerFilePath(), newTaskManagerDataStorageFile.getCanonicalPath()); //Throws IOException
//            
//            //Checks that taskManagerFilePath attribute in testConfig.json has been updated to reflect new task manager data storage file path
//            Config testConfigJSONFileToConfig = ConfigUtil.readConfig(testConfig.getConfigFilePath()).get(); //Throws DataConversionException
//            assertEquals(testConfigJSONFileToConfig.getTaskManagerFilePath(), newTaskManagerDataStorageFile.getCanonicalPath()); //Throws IOException
            
    	} catch (IOException ex) {
    		System.out.println("IOException thrown while retrieving canonical file path of newTaskManagerDataStorageFile "
    				+ "in assertSetStorageCommandSuccess() in SetStorageCommandTest.");
    	} 
//    		catch (DataConversionException ex) {
//    		System.out.println("DataConversionException thrown while converting \"testConfig.json\" to Config instance "
//    				+ "in assertSetStorageCommandSuccess() in SetStorageCommandTest.");
//    	}
    }
    
    private void runSetStorageCommand(String folderFilePath, String fileName){
    	assert folderFilePath!= null;
    	assert fileName!= null;
    	
    	String setStorageCommand = "set-storage " + folderFilePath + " save-as " + fileName;
    	commandBox.runCommand(setStorageCommand);
    }
}