package seedu.taskscheduler.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.taskscheduler.model.ReadOnlyTaskScheduler;
import seedu.taskscheduler.model.TaskScheduler;
import seedu.taskscheduler.model.UserPrefs;
import seedu.taskscheduler.storage.StorageManager;
import seedu.taskscheduler.testutil.TypicalTestTasks;

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
    public void taskSchedulerReadSave() throws Exception {
        TaskScheduler original = new TypicalTestTasks().getTypicalTaskScheduler();
        storageManager.saveTaskScheduler(original);
        ReadOnlyTaskScheduler retrieved = storageManager.readTaskScheduler().get();
        assertEquals(original, new TaskScheduler(retrieved));
        //More extensive testing of TaskScheduler saving/reading is done in XmlTaskSchedulerStorageTest
    }


}
