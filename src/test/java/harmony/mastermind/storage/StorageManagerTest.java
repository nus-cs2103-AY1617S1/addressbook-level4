package harmony.mastermind.storage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.storage.DataSavingExceptionEvent;
import harmony.mastermind.commons.events.storage.RelocateFilePathEvent;

import harmony.mastermind.model.ReadOnlyTaskManager;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.UserPrefs;
import harmony.mastermind.storage.StorageManager;
import harmony.mastermind.testutil.EventsCollector;
import harmony.mastermind.testutil.TypicalTestTasks;

public class StorageManagerTest {

    private StorageManager storageManager;
    //@@author A0139194X
    private final String FILEPATH_ENDING_WITH_SLASH = "TestFile/";
    private final String FILEPATH_NOT_ENDING_WITH_SLASH = "TestFile";
    private final String ORIGINAL_FOLDER = "data/mastermind.xml";
    
    //@@author
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    //@@author A0139194X
    @Rule
    public ExpectedException thrown = ExpectedException.none();
  
    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    /*
     * Note: This is an integration test that verifies the StorageManager is
     * properly wired to the {@link JsonUserPrefStorage} class. More extensive
     * testing of UserPref saving/reading is done in {@link
     * JsonUserPrefStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void taskManagerReadSave() throws Exception {
        TaskManager original = new TypicalTestTasks().getTypicalTaskManager();
        storageManager.saveTaskManager(original);
        ReadOnlyTaskManager retrieved = storageManager.readTaskManager().get();

        assertEquals(original, new TaskManager(retrieved));
        // More extensive testing of TaskManager saving/reading is done in
        // XmlTaskManagerStorageTest
    }

    @Test
    public void getTaskManagerFilePath() {
        assertNotNull(storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleTaskManagerChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that throws an
        // exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskManagerStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskManagerChangedEvent(new TaskManagerChangedEvent(new TaskManager()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
    
    //@@author A0139194X
    @Test
    public void correctFilePathFormat_nullFilePath_assertionFailure() {
        thrown.expect(AssertionError.class);
        storageManager.correctFilePathFormat(null);
    }
    
    //@@author A0139194X
    @Test
    public void correctFilePathFormat_filePathEndingWithSlash_success() {
        String result = storageManager.correctFilePathFormat(FILEPATH_ENDING_WITH_SLASH);
        assertEquals(result, FILEPATH_ENDING_WITH_SLASH + "mastermind.xml");
    }
    
    //@@author A0139194X
    @Test
    public void correctFilePathFormat_filePathNotEndingWithSlash_success() {
        String result = storageManager.correctFilePathFormat(FILEPATH_NOT_ENDING_WITH_SLASH);
        assertEquals(result, FILEPATH_NOT_ENDING_WITH_SLASH + "/mastermind.xml");
    }
    
    //@@author A0139194X
    @Test
    public void handleRelocateEvent_nullEvent_assertionFailure() {
        thrown.expect(AssertionError.class);
        storageManager.handleRelocateEvent(null);
    }
    
    //@@author A0139194X
    @Test
    public void handleRelocateEvent_nullEventFilePath_assertionFailure() {
        thrown.expect(AssertionError.class);
        RelocateFilePathEvent event = new RelocateFilePathEvent(null);
        storageManager.handleRelocateEvent(event);
    }
    
    //@@author A0139194X
    @Test
    public void handleRelocateEvent_unwrittableFilePath_IOExceptionThrown() {
        String filePath = storageManager.getTaskManagerFilePath() + "/mastermind.xml";
        RelocateFilePathEvent event = new RelocateFilePathEvent("");
        storageManager.handleRelocateEvent(event);
        assertEquals(filePath, storageManager.getTaskManagerFilePath() + "/mastermind.xml");
        reset();
    }
    
    //@@author A0139194X
    @Test
    public void updateConfig_nullInput_assertionFailure() {
        thrown.expect(AssertionError.class);
        storageManager.updateConfig(null);
    }
    
    //resets the config to the original 
    public void reset() {
        storageManager.updateConfig(ORIGINAL_FOLDER);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskManagerStorageExceptionThrowingStub extends XmlTaskManagerStorage {

        public XmlTaskManagerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskManager(ReadOnlyTaskManager addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
    
    /**@@author A0139194X
     * A Stub class to store config.json
     */
    class ConfigStub extends Config {
        
        Config config;
        
        public ConfigStub(String filePath) {
            config = new Config();
            config.setTaskManagerFilePath(filePath);
        }
    }
    
}
