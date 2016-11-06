package guitests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.menion.commons.core.Config;
import seedu.menion.commons.exceptions.DataConversionException;
import seedu.menion.commons.util.ConfigUtil;
import seedu.menion.commons.util.FileUtil;
import seedu.menion.commons.util.XmlUtil;
import seedu.menion.logic.commands.ModifyStoragePathCommand;
import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;
import seedu.menion.storage.XmlFileStorage;
import seedu.menion.storage.XmlSerializableActivityManager;
import seedu.menion.testutil.TypicalTestActivities;

//@@author A0139515A

public class ModifyStoragePathCommandTest extends ActivityManagerGuiTest {
	
	Config originalConfig;
	File originalFile;
	String originalStoragePath;
	XmlSerializableActivityManager originalData;
	
	@Test
	public void modifyStoragePath() throws DataConversionException, JAXBException, IOException {
		
		saveOriginalConfig();
    	
		//testing if the file in the new location created has the same content
		String filePath = ModifyStoragePathCommand.TEST_STORAGE_PATH;		
		TypicalTestActivities typicalTestActivities = new TypicalTestActivities();		
		ActivityManager original = typicalTestActivities.getTypicalActivityManager();
		
        commandBox.runCommand("modify " + filePath);         
        XmlActivityManagerStorage xmlActivityManagerStorage = new XmlActivityManagerStorage(filePath);       
        ReadOnlyActivityManager am =  xmlActivityManagerStorage.readActivityManager(filePath).get();       
        ActivityManager after = new ActivityManager(am);       
        assertEquals(original, after);
        
        commandBox.runCommand("modify default");
        assertEquals(original, after);
        
        //for invalid command
        commandBox.runCommand("modify"); 
        assertResultMessage(ModifyStoragePathCommand.MESSAGE_FAILURE);
        
        //revert to original file path
        commandBox.runCommand("undo m"); 
        commandBox.runCommand("undo m"); 
        commandBox.runCommand("undo m");
        restoreOriginalConfig();
	}
	
	private void restoreOriginalConfig() throws IOException, FileNotFoundException, JAXBException {
		FileUtil.createIfMissing(originalFile);
        XmlUtil.saveDataToFile(originalFile, originalData);
	}

	private void saveOriginalConfig() throws IOException, DataConversionException, FileNotFoundException {
		try {
            Optional<Config> configOptional = ConfigUtil.readConfig(Config.DEFAULT_CONFIG_FILE);
            originalConfig = configOptional.orElse(Config.getInstance());
        } catch (DataConversionException e) {
            originalConfig = Config.getInstance();
        }
    	originalFile = new File(originalConfig.getActivityManagerFilePath());
    	FileUtil.createIfMissing(originalFile);
    	originalData = XmlFileStorage.loadDataFromSaveFile(originalFile);
    	originalStoragePath = originalConfig.getActivityManagerFilePath();
	}
}