package seedu.address.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.TypicalTestItems;

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
        storageManager = new StorageManager(getTempFilePath("tb"), getTempFilePath("prefs"));
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
    public void taskBookReadSave() throws Exception {
        TaskBook original = new TypicalTestItems().getTypicalTaskBook();
        storageManager.saveTaskBook(original);
        ReadOnlyTaskBook retrieved = storageManager.readTaskBook().get();
        assertEquals(original, new TaskBook(retrieved));
        //More extensive testing of TaskBook saving/reading is done in XmlTaskBookStorageTest
    }

    @Test
    public void getTaskBookFilePath(){
        assertNotNull(storageManager.getTaskBookFilePath());
    }

    @Test
    public void handleTaskBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskBookStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskBookChangedEvent(new TaskBookChangedEvent(new TaskBook()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskBookStorageExceptionThrowingStub extends XmlTaskBookStorage{

        public XmlTaskBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
            throw new IOException("dummy exception; this is expected on save");
        }
    }


}
