package seedu.address.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.testutil.TypicalTestTasks;
import seedu.emeraldo.commons.events.model.EmeraldoChangedEvent;
import seedu.emeraldo.commons.events.storage.DataSavingExceptionEvent;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.ReadOnlyEmeraldo;
import seedu.emeraldo.model.UserPrefs;
import seedu.emeraldo.storage.JsonUserPrefsStorage;
import seedu.emeraldo.storage.Storage;
import seedu.emeraldo.storage.StorageManager;
import seedu.emeraldo.storage.XmlEmeraldoStorage;
import seedu.address.testutil.EventsCollector;

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
    public void addressBookReadSave() throws Exception {
        Emeraldo original = new TypicalTestTasks().getTypicalEmeraldo();
        storageManager.saveEmeraldo(original);
        ReadOnlyEmeraldo retrieved = storageManager.readEmeraldo().get();
        assertEquals(original, new Emeraldo(retrieved));
        //More extensive testing of AddressBook saving/reading is done in XmlAddressBookStorageTest
    }

    @Test
    public void getAddressBookFilePath(){
        assertNotNull(storageManager.getEmeraldoFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleEmeraldoChangedEvent(new EmeraldoChangedEvent(new Emeraldo()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlEmeraldoStorage{

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveEmeraldo(ReadOnlyEmeraldo addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
