package seedu.address.storage;

import java.io.File;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import guitests.AddressBookGuiTest;

//@@author A0125680H
public class SaveCommandTest extends AddressBookGuiTest {
    private static final String TEST_SAVE_DIRECTORY = "src/test/data/testfile.xml";
    private static final String TEST_SAVE_DIRECTORY_NO_EXTENSION = "src/test/data/testfile";
    
    @Test
    public void save_withDefaultTestPath() {
        saveFile(TEST_SAVE_DIRECTORY);
        assertSaveFileExists(TEST_SAVE_DIRECTORY);
    }
    
    @Test
    public void save_withDefaultTestPath_noXmlExtension() {
        saveFile(TEST_SAVE_DIRECTORY_NO_EXTENSION);
        assertSaveFileExists(TEST_SAVE_DIRECTORY_NO_EXTENSION + ".xml");
    }
    
    private void saveFile(String saveDirectory) {
        String saveCommand = "save " + saveDirectory;
        
        commandBox.runCommand(saveCommand);
    }
    
    private void assertSaveFileExists(String saveDirectory) {
        File testSaveFile = new File(saveDirectory);
        
        assertTrue(testSaveFile.exists());
        
        testSaveFile.delete();
    }
}
