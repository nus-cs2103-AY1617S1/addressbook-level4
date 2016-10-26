package seedu.todo.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.todo.commons.events.model.ToDoListChangedEvent;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.model.ReadOnlyToDoList;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.UserPrefs;
import seedu.todo.testutil.EventsCollector;
import seedu.todo.testutil.TypicalTestTasks;

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
        DoDoBird original = new TypicalTestTasks().getTypicalToDoList();
        storageManager.saveToDoList(original);
        ReadOnlyToDoList retrieved = storageManager.readToDoList().get();
        assertEquals(original, new DoDoBird(retrieved));
        //More extensive testing of ToDoList saving/reading is done in XmlToDoListStorageTest
    }

    @Test
    public void getToDoListFilePath(){
        assertNotNull(storageManager.getToDoListFilePath());
    }

    @Test
    public void handleToDoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlToDoListStorageExceptionThrowingStub("dummy"), 
                new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleToDoListChangedEvent(new ToDoListChangedEvent(new DoDoBird()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlToDoListStorageExceptionThrowingStub extends XmlToDoListStorage{

        public XmlToDoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoList(ReadOnlyToDoList addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
