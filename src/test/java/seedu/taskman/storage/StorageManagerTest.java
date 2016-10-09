package seedu.taskman.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.taskman.commons.events.model.TaskManChangedEvent;
import seedu.taskman.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.ReadOnlyTaskMan;
import seedu.taskman.model.UserPrefs;
import seedu.taskman.testutil.TypicalTestTasks;
import seedu.taskman.testutil.EventsCollector;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
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
    public void taskManReadSave() throws Exception {
        TaskMan original = new TypicalTestTasks().getTypicalTaskMan();
        storageManager.saveTaskMan(original);
        ReadOnlyTaskMan retrieved = storageManager.readTaskMan().get();
        assertEquals(original, new TaskMan(retrieved));
        //More extensive testing of TaskMan saving/reading is done in XmlTaskManStorageTest
    }

    @Test
    public void getTaskManFilePath(){
        assertNotNull(storageManager.getTaskManFilePath());
    }

    @Test
    public void handleTaskManChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskManStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskManChangedEvent(new TaskManChangedEvent(new TaskMan()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskManStorageExceptionThrowingStub extends XmlTaskManStorage {

        public XmlTaskManStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskMan(ReadOnlyTaskMan taskMan, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
