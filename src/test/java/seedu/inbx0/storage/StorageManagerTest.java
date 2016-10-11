package seedu.inbx0.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.inbx0.commons.events.model.TaskListChangedEvent;
import seedu.inbx0.commons.events.storage.DataSavingExceptionEvent;
import seedu.inbx0.model.TaskList;
import seedu.inbx0.model.ReadOnlyTaskList;
import seedu.inbx0.model.UserPrefs;
import seedu.inbx0.storage.JsonUserPrefsStorage;
import seedu.inbx0.storage.Storage;
import seedu.inbx0.storage.StorageManager;
import seedu.inbx0.storage.XmlTaskListStorage;
import seedu.inbx0.testutil.EventsCollector;
import seedu.inbx0.testutil.TypicalTestTasks;

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
    public void addressBookReadSave() throws Exception {
        TaskList original = new TypicalTestTasks().getTypicalTaskList();
        storageManager.saveTaskList(original);
        ReadOnlyTaskList retrieved = storageManager.readTaskList().get();
        assertEquals(original, new TaskList(retrieved));
        //More extensive testing of TaskList saving/reading is done in XmlTaskListStorageTest
    }

    @Test
    public void getAddressBookFilePath(){
        assertNotNull(storageManager.getTaskListFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskListChangedEvent(new TaskListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlTaskListStorage{

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskList(ReadOnlyTaskList addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
