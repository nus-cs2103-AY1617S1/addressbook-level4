package w15c2.tusk.storage;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.storage.DataSavingExceptionEvent;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.UserPrefs;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.model.task.TaskManager;
import w15c2.tusk.storage.alias.XmlAliasStorage;
import w15c2.tusk.storage.task.TaskStorage;
import w15c2.tusk.storage.task.TaskStorageManager;
import w15c2.tusk.storage.task.XmlTaskManagerStorage;
import w15c2.tusk.testutil.EventsCollector;
import w15c2.tusk.testutil.TypicalTestTasks;

public class StorageManagerTest {
    private TaskStorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new TaskStorageManager(getTempFilePath("tm"), getTempFilePath("alias"), getTempFilePath("prefs"));
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
    public void taskManagerReadSave() throws Exception {
        TaskManager originalTaskManager = new TypicalTestTasks().getTypicalTaskManager();
        storageManager.saveTaskManager(originalTaskManager.getTasks());
        storageManager.saveAlias(originalTaskManager.getAliasCollection());

        UniqueItemCollection<Task> retrievedTaskManager = storageManager.readTaskManager().get();
        UniqueItemCollection<Alias> retrievedAlias = storageManager.readAlias().get();
        assertEquals(originalTaskManager, new TaskManager(retrievedTaskManager, retrievedAlias));
        //More extensive testing of TaskManager saving/reading is done in XmlTaskManagerStorageTest
    }

    @Test
    public void getTaskManagerFilePath(){
        assertNotNull(storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleTaskManagerChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a TaskStorageManager while injecting a stub that throws an exception when the save method is called
        TaskStorage storage = new TaskStorageManager(new XmlTaskManagerStorageExceptionThrowingStub("dummy"), new XmlAliasStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTaskManagerChangedEvent(new TaskManagerChangedEvent(new UniqueItemCollection<Task>()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskManagerStorageExceptionThrowingStub extends XmlTaskManagerStorage{

        public XmlTaskManagerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskManager(UniqueItemCollection<Task> taskManager, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
    
    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAliasStorageExceptionThrowingStub extends XmlAliasStorage{

        public XmlAliasStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAlias(UniqueItemCollection<Alias> alias, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
