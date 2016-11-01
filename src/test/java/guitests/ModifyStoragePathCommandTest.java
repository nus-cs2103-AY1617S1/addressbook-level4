package guitests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.menion.model.ActivityManager;
import seedu.menion.model.ReadOnlyActivityManager;
import seedu.menion.storage.XmlActivityManagerStorage;
import seedu.menion.testutil.TypicalTestActivities;

//@@author A0139515A
public class ModifyStoragePathCommandTest extends ActivityManagerGuiTest {
  
	@Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test
	public void modifyStoragePath() throws Exception {
		
		//testing if the file in the new location created has the same content
		String filePath = testFolder.getRoot().getPath() + "testMenion.xml";		
		TypicalTestActivities typicalTestActivities = new TypicalTestActivities();		
		ActivityManager original = typicalTestActivities.getTypicalActivityManager();
        String root = System.getProperty("user.home");
        String newPath = root + File.separator + filePath;
        commandBox.runCommand("modify " + filePath);         
        XmlActivityManagerStorage xmlActivityManagerStorage = new XmlActivityManagerStorage(newPath);       
        ReadOnlyActivityManager am =  xmlActivityManagerStorage.readActivityManager(newPath).get();       
        ActivityManager after = new ActivityManager(am);       
        assertEquals(original, after);
        
        commandBox.runCommand("modify menionStorage/menion.xml");
        //for invalid command
        commandBox.runCommand("modify"); 
        assertResultMessage("Please provide a valid storage path!");
	}
}
