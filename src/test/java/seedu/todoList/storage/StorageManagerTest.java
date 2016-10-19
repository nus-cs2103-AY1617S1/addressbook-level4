package seedu.todoList.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.todoList.commons.events.model.EventListChangedEvent;
import seedu.todoList.commons.events.model.TodoListChangedEvent;
import seedu.todoList.commons.events.storage.DataSavingExceptionEvent;
import seedu.todoList.model.ReadOnlyTaskList;
//import seedu.todoList.model.ReadOnlyTodoList;
import seedu.todoList.model.TaskList;
import seedu.todoList.model.UserPrefs;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.storage.JsonUserPrefsStorage;
import seedu.todoList.storage.Storage;
import seedu.todoList.storage.StorageManager;
import seedu.todoList.storage.XmlTaskListStorage;
import seedu.todoList.testutil.EventsCollector;
import seedu.todoList.testutil.TypicalTestEvent;
import seedu.todoList.testutil.TypicalTestTask;

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
        storageManager = new StorageManager(getTempFilePath("ab"), null, null, getTempFilePath("prefs"));
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
    public void TodoListReadSave() throws Exception {
        TaskList original = new TypicalTestTask().getTypicalTodoList();
        storageManager.saveTodoList(original);
        ReadOnlyTaskList retrieved = storageManager.readTodoList().get();
        assertEquals(original, new TaskList(retrieved));
        //More extensive testing of TodoList saving/reading is done in XmlTodoListStorageTest
    }
    
    @Test
    public void EventListReadSave() throws Exception {
        TaskList original = new TypicalTestEvent().getTypicalEventList();
        storageManager.saveEventList(original);
        ReadOnlyTaskList retrieved = storageManager.readEventList().get();
        assertEquals(original, new TaskList(retrieved));
       
    }

    @Test
    public void getTodoListFilePath(){
        assertNotNull(storageManager.getTodoListFilePath());
    }
    
    @Test
    public void getEventListFilePath(){
        assertNotNull(storageManager.getEventListFilePath());
    }

    @Test
    public void handleTodoListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTodoListStorageExceptionThrowingStub("dummy"), null, null, new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTodoListChangedEvent(new TodoListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }
    
    @Test
    public void handleEventListChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(null, new XmlEventListStorageExceptionThrowingStub("dummy"), null, new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEventListChangedEvent(new EventListChangedEvent(new TaskList()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }



    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTodoListStorageExceptionThrowingStub extends XmlTaskListStorage{

        public XmlTodoListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        //@Override
        public void saveTodoList(ReadOnlyTaskList TodoList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
    
    class XmlEventListStorageExceptionThrowingStub extends XmlTaskListStorage{

        public XmlEventListStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        //@Override
        public void saveEventList(ReadOnlyTaskList EventList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
