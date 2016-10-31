package tars.storage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import tars.model.Tars;
import tars.model.ReadOnlyTars;
import tars.model.UserPrefs;
import tars.storage.StorageManager;
import tars.testutil.TypicalTestTasks;

import static org.junit.Assert.assertEquals;

public class StorageManagerTest {

    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setUp() {
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
    public void tarsReadSave() throws Exception {
        Tars original = new TypicalTestTasks().getTypicalTars();
        storageManager.saveTars(original);
        ReadOnlyTars retrieved = storageManager.readTars().get();
        assertEquals(original, new Tars(retrieved));
        //More extensive testing of Tars saving/reading is done in XmlTarsStorageTest
    }


}
