package seedu.todo.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todo.model.TodoList;
import seedu.todo.model.UserPrefs;
import seedu.todo.model.task.ImmutableTask;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
     * {@link UserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link UserPrefsStorageTest} class.
     */

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs();
        assertEquals(original, retrieved);
    }

}
