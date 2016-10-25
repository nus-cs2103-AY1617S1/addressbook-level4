package seedu.malitio.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.malitio.testutil.TypicalTestTasks;
import seedu.malitio.commons.events.model.MalitioChangedEvent;
import seedu.malitio.commons.events.storage.DataSavingExceptionEvent;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.UserPrefs;
import seedu.malitio.storage.JsonUserPrefsStorage;
import seedu.malitio.storage.Storage;
import seedu.malitio.storage.StorageManager;
import seedu.malitio.storage.XmlMalitioStorage;
import seedu.malitio.testutil.EventsCollector;

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
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
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
    public void malitioReadSave() throws Exception {
        Malitio original = new TypicalTestTasks().getTypicalMalitio();
        storageManager.saveMalitio(original);
        ReadOnlyMalitio retrieved = storageManager.readMalitio().get();
        assertEquals(original, new Malitio(retrieved));
        //More extensive testing of malitio saving/reading is done in XmlmalitioStorageTest
    }

    @Test
    public void getmalitioFilePath(){
        assertNotNull(storageManager.getMalitioFilePath());
    }

    @Test
    public void handlemalitioChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlmalitioStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleMalitioChangedEvent(new MalitioChangedEvent(new Malitio()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlmalitioStorageExceptionThrowingStub extends XmlMalitioStorage{

        public XmlmalitioStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveMalitio(ReadOnlyMalitio malitio, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
