package seedu.unburden.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.unburden.commons.events.model.ListOfTaskChangedEvent;
import seedu.unburden.commons.events.storage.DataSavingExceptionEvent;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.ReadOnlyListOfTask;
import seedu.unburden.model.UserPrefs;
import seedu.unburden.storage.JsonUserPrefsStorage;
import seedu.unburden.storage.Storage;
import seedu.unburden.storage.StorageManager;
import seedu.unburden.storage.XmlTaskListStorage;
import seedu.unburden.testutil.EventsCollector;
import seedu.unburden.testutil.TypicalTestTasks;

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

    //@Test
    public void taskListReadSave() throws Exception {
        ListOfTask original = new TypicalTestTasks().getTypicalListOfTask();
        storageManager.saveTaskList(original);
        ReadOnlyListOfTask retrieved = storageManager.readTaskList().get();
        assertEquals(original, new ListOfTask(retrieved));
        //More extensive testing of ListOfTask saving/reading is done in XmlTaskListStorageTest
    }

    @Test
    public void getTaskListFilePath(){
        assertNotNull(storageManager.getTaskListFilePath());
    }

    @Test
    public void handleTaskListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskListStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleListOfTaskChangedEvent(new ListOfTaskChangedEvent(new ListOfTask()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskListStorageExceptionThrowingStub extends XmlTaskListStorage{

        public XmlTaskListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskList(ReadOnlyListOfTask addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
