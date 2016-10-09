package seedu.address.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.events.model.ToDoListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyToDoList;
import seedu.address.model.ToDoList;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.EventsCollector;
import seedu.address.testutil.ToDoBuilder;
import seedu.address.testutil.ToDoListBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private EventsCollector eventCollector;
    private File toDoListFile;
    private File userPrefsFile;

    @Before
    public void setup() throws IOException {
        toDoListFile = folder.newFile();
        userPrefsFile = folder.newFile();
        storageManager = new StorageManager(
            toDoListFile.getAbsolutePath(),
            userPrefsFile.getAbsolutePath()
        );
        eventCollector = new EventsCollector();
    }

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
     */

    @Test
    public void userPrefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void toDoListReadSave() throws Exception {
        ToDoList original = ToDoListBuilder.getSample();
        storageManager.saveToDoList(original);
        ReadOnlyToDoList retrieved = storageManager.readToDoList().get();
        assertEquals(original, new ToDoList(retrieved));
        // More extensive testing of AddressBook saving/reading is done in XmlToDoListStorageTest
    }

    @Test
    public void getToDoListFilePath(){
        assertTrue(storageManager.getToDoListFilePath() != null);
    }

    @Test
    public void handleToDoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new ExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        storage.handleToDoListChangedEvent(new ToDoListChangedEvent(new ToDoList()));
        assertTrue(eventCollector.hasCollectedEvent(DataSavingExceptionEvent.class));
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    private static class ExceptionThrowingStub extends XmlToDoListStorage {

        public ExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveToDoList(ReadOnlyToDoList toDoList, String filePath) throws IOException {
            throw new IOException();
        }
    }

}
