package w15c2.tusk.storage;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;
import org.junit.rules.TemporaryFolder;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.events.model.TaskManagerChangedEvent;
import w15c2.tusk.commons.events.storage.DataSavingExceptionEvent;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.ModelManager;
import w15c2.tusk.model.UserPrefs;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.storage.alias.XmlAliasStorage;
import w15c2.tusk.storage.task.XmlTaskManagerStorage;
import w15c2.tusk.testutil.EventsCollector;
import w15c2.tusk.testutil.TypicalTestTasks;

/**
 * Tests StorageManager
 */
public class StorageManagerTest {
    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("tm"), getTempFilePath("alias"), getTempFilePath("prefs"));
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
        ModelManager originalModelManager = new TypicalTestTasks().getTypicalModelManager();
        storageManager.saveTaskManager(originalModelManager.getTasks());
        storageManager.saveAlias(originalModelManager.getAliasCollection());

        UniqueItemCollection<Task> retrievedModelManager = storageManager.readTaskManager().get();
        UniqueItemCollection<Alias> retrievedAlias = storageManager.readAlias().get();
        assertEquals(originalModelManager, new ModelManager(retrievedModelManager, retrievedAlias));
        //More extensive testing of ModelManager saving/reading is done in XmlModelManagerStorageTest
    }

    @Test
    public void getTaskManagerFilePath(){
        assertNotNull(storageManager.getTaskManagerFilePath());
    }

    @Test
    public void handleTaskManagerChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskManagerStorageExceptionThrowingStub("dummy"), new XmlAliasStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
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
