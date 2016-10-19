package seedu.address.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.model.TaskMaster;
import seedu.address.model.ReadOnlyTaskMaster;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalTestTasks;
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

    @Test
    public void addressBookReadSave() throws Exception {
        TaskMaster original = new TypicalTestTasks().getTypicalTaskList();
        storageManager.saveTaskList(original);
        ReadOnlyTaskMaster retrieved = storageManager.readTaskList().get();
        assertEquals(original, new TaskMaster(retrieved));
        
        //More extensive testing of TaskList saving/reading is done in XmlTaskListStorageTest
    }


}
