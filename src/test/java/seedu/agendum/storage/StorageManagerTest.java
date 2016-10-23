package seedu.agendum.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.events.model.ChangeSaveLocationRequestEvent;
import seedu.agendum.commons.events.model.ToDoListChangedEvent;
import seedu.agendum.commons.events.storage.DataSavingExceptionEvent;
import seedu.agendum.model.ToDoList;
import seedu.agendum.model.ReadOnlyToDoList;
import seedu.agendum.model.UserPrefs;
import seedu.agendum.testutil.TypicalTestTasks;
import seedu.agendum.testutil.EventsCollector;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"), new Config());
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
    public void toDoListReadSave() throws Exception {
        ToDoList original = new TypicalTestTasks().getTypicalToDoList();
        storageManager.saveToDoList(original);
        ReadOnlyToDoList retrieved = storageManager.readToDoList().get();
        assertEquals(original, new ToDoList(retrieved));
        //More extensive testing of ToDoList saving/reading is done in XmlToDoListStorageTest
    }

    @Test
    public void getToDoListFilePath(){
        assertNotNull(storageManager.getToDoListFilePath());
    }

    @Test
    public void handleToDoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlToDoListStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"), new Config());
        EventsCollector eventCollector = new EventsCollector();
        storage.handleToDoListChangedEvent(new ToDoListChangedEvent(new ToDoList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
    
    @Test
    public void handleSaveLocationChangedEvent_validFilePath() {
        String validPath = "data/test.xml";
        storageManager.handleChangeSaveLocationRequestEvent(new ChangeSaveLocationRequestEvent(validPath));
        assertEquals(storageManager.getToDoListFilePath(), validPath);
    }
    
    public void setToDoListFilePath() {
        // null
        thrown.expect(AssertionError.class);
        storageManager.setToDoListFilePath(null);

        // empty string
        thrown.expect(AssertionError.class);
        storageManager.setToDoListFilePath("");

        // invalid file path
        thrown.expect(AssertionError.class);
        storageManager.setToDoListFilePath("1:/.xml");
        
        // valid file path
        String validPath = "test/test.xml";
        storageManager.setToDoListFilePath(validPath);
        assertEquals(validPath, storageManager.getToDoListFilePath());
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlToDoListStorageExceptionThrowingStub extends XmlToDoListStorage{

        public XmlToDoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
