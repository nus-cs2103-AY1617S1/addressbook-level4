package seedu.flexitrack.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.flexitrack.commons.events.model.FlexiTrackChangedEvent;
import seedu.flexitrack.commons.events.storage.DataSavingExceptionEvent;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.UserPrefs;
import seedu.flexitrack.testutil.EventsCollector;
import seedu.flexitrack.testutil.TypicalTestTasks;

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
     * Note: This is an integration test that verifies the StorageManager is
     * properly wired to the {@link JsonUserPrefsStorage} class. More extensive
     * testing of UserPref saving/reading is done in {@link
     * JsonUserPrefsStorageTest} class.
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
        FlexiTrack original = new TypicalTestTasks().getTypicalFlexiTrack();
        storageManager.saveFlexiTrack(original);
        ReadOnlyFlexiTrack retrieved = storageManager.readFlexiTrack().get();
        assertEquals(original, new FlexiTrack(retrieved));
        // More extensive testing of AddressBook saving/reading is done in
        // XmlAddressBookStorageTest
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getFlexiTrackFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that throws an
        // exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleFlexiTrackChangedEvent(new FlexiTrackChangedEvent(new FlexiTrack()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlFlexiTrackStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveFlexiTrack(ReadOnlyFlexiTrack addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
