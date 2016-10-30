package guitests;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.ConfigUtil;
import seedu.malitio.model.Malitio;
import seedu.malitio.storage.StorageManager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Assumes storage and file utils are working 
 * 
 */
//@@author a0126633j
public class SaveCommandTest extends MalitioGuiTest {
    private static final String DEFAULT_FILE_NAME = "malitio.xml";
    private static final String TEST_FILE_PATH = "src/test/data/tempDataForSaveCommand/test1/";
    private static final String DEFAULT_FILE_PATH = "src/test/data/tempDataForSaveCommand/";
   
    // a storage manager to read data from xml file
    private StorageManager storageManager = new StorageManager("random", "random");
    
    private Malitio original = getInitialData();
    private String originalFilePath;
    
    @Before
    public void getOriginalFilePath() throws DataConversionException {
        originalFilePath = ConfigUtil.readConfig("./config.json/").get().getMalitioFilePath();
    }
    
    @Test
    public void save() throws DataConversionException, IOException {
            
    //save default file location
    commandBox.runCommand("save " + DEFAULT_FILE_PATH);
    assertSaveSuccessful(DEFAULT_FILE_PATH);
   
    //save in new file location
    commandBox.runCommand("save " + TEST_FILE_PATH);
    assertSaveSuccessful(TEST_FILE_PATH);
    assertFileDeletionSuccessful(DEFAULT_FILE_PATH);
    
    //save default file location again
    commandBox.runCommand("save " + DEFAULT_FILE_PATH);
    assertSaveSuccessful(DEFAULT_FILE_PATH);
    assertFileDeletionSuccessful(TEST_FILE_PATH);
    
    //orginal save file location should be preserved after the tests
    ConfigUtil.changeMalitioSaveDirectory(originalFilePath);
    }

    /**
     * Asserts new file is present and consistent with Malitio data
     * @throws DataConversionException 
     * @throws IOException 
     */
    public void assertSaveSuccessful(String newFileLocation) throws DataConversionException, IOException {
        File f = new File(newFileLocation + DEFAULT_FILE_NAME);
        if(f.exists()) {
            assertEquals(original, new Malitio(storageManager.readMalitio(newFileLocation + DEFAULT_FILE_NAME).get()));
        } else {
            assertTrue(false);
        }
    }

    /**
     * Asserts old file is successfully deleted 
     */
    public void assertFileDeletionSuccessful(String oldFileLocation) {
        File f = new File(oldFileLocation + DEFAULT_FILE_NAME);
        assertFalse(f.exists());
    }
}