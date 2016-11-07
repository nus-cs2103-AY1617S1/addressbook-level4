package guitests;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import org.junit.Test;

import seedu.task.commons.core.Config;
import seedu.task.commons.exceptions.DataConversionException;
import seedu.task.logic.commands.SaveCommand;
import seedu.task.model.item.UniqueTaskList.DuplicateTaskException;
import seedu.task.storage.JsonConfigStorage;

//@@author A0125534L

/**
 * Responsible for testing the GUI execution of SaveCommand
 * 
 */

//------------------------Tests for Valid arguments----------------
	/*
	 * Command input: "save" , "save [filePath]"
	 * 
	 * Valid filePath 
	 * : C:/folder 
	 * : C:/folder/folder 
	 * : D: 
	 * : [Drive]:[filePath]
	 * 
	 */


public class SaveCommandTest extends TaskBookGuiTest {
   
    private static final String CONFIG_JSON = "config.json";
    private static final String CONFIG_LOCATION = "./src/test/data/SaveCommandTest";
    
    //valid filePath
    @Test
    public void saveTo_Valid_FilePath() throws DataConversionException, IOException, DuplicateTaskException {
        String testFilePath = "./src/test/data/SaveCommandTest/newStorageLocation/";
        commandBox.runCommand("save " + testFilePath);
        assertWriteToJsonSuccess();
       
    }
    
    /** NOTE: 	because of the way SaveStorageLocationCommand works, after running this command
     *          config.json in TaskBook saves the test data so this method is necessary to reset
     *          config.json to default data
     * */
    
    //Reset filePath
    @Test
    public void reset_ConfigFile() throws IOException {
        Config config = new Config();
        config.setAppTitle("Dowat");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("preferences.json");
        config.setTaskBookFilePath("data/Dowat.xml");
        config.setTaskBookName("MyTaskManager");
        SaveCommand.setConfig(config);
        
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_JSON);
        jsonConfigStorage.saveConfigFile(config);
    }
    
    private void assertWriteToJsonSuccess() throws DataConversionException {
        JsonConfigStorage jsonConfigStorage = new JsonConfigStorage(CONFIG_LOCATION);
        Optional<Config> config = jsonConfigStorage.readConfig(CONFIG_JSON);
        assert(config.isPresent());
    } 
    
    
}