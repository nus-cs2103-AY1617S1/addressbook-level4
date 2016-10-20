package seedu.tasklist.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.tasklist.model.UserPrefs;
import seedu.tasklist.storage.StorageManager;
import static org.junit.Assert.assertEquals;

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
     * {@link JsonUserPrefStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }
/**
    @Test
    public void taskListReadSave() throws Exception {
        TaskList original = new TypicalTestTasks().getTypicalTaskList();
        storageManager.saveTaskList(original);
        ReadOnlyTaskList retrieved = storageManager.readTaskList().get();
        assertEquals(original, new TaskList(retrieved));
        //More extensive testing of TaskList saving/reading is done in XmlTaskListStorageTest
    }
*/

}
