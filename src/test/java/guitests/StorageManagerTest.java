//package seedu.whatnow.storage;
//
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.TemporaryFolder;
//
//import seedu.whatnow.testutil.TypicalTestTasks;
//import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
//import seedu.whatnow.commons.events.storage.DataSavingExceptionEvent;
//import seedu.whatnow.model.ReadOnlyWhatNow;
//import seedu.whatnow.model.UserPrefs;
//import seedu.whatnow.model.WhatNow;
//import seedu.whatnow.storage.JsonUserPrefsStorage;
//import seedu.whatnow.storage.Storage;
//import seedu.whatnow.storage.StorageManager;
//import seedu.whatnow.storage.XmlWhatNowStorage;
//import seedu.whatnow.testutil.EventsCollector;
//
//import java.io.IOException;
//
//import static junit.framework.TestCase.assertNotNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class StorageManagerTest {
//
//    private StorageManager storageManager;
//
//    @Rule
//    public TemporaryFolder testFolder = new TemporaryFolder();
//
//
//    @Before
//    public void setup() {
//        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
//    }
//
//
//    private String getTempFilePath(String fileName) {
//        return testFolder.getRoot().getPath() + fileName;
//    }
//
//
//    /*
//     * Note: This is an integration test that verifies the StorageManager is properly wired to the
//     * {@link JsonUserPrefsStorage} class.
//     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
//     */
//
//    @Test
//    public void prefsReadSave() throws Exception {
//        UserPrefs original = new UserPrefs();
//        original.setGuiSettings(300, 600, 4, 6);
//        storageManager.saveUserPrefs(original);
//        UserPrefs retrieved = storageManager.readUserPrefs().get();
//        assertEquals(original, retrieved);
//    }
//
//    @Test
//    public void whatNowReadSave() throws Exception {
//        WhatNow original = new TypicalTestTasks().getTypicalWhatNow();
//        storageManager.saveWhatNow(original);
//        ReadOnlyWhatNow retrieved = storageManager.readWhatNow().get();
//        assertEquals(original, new WhatNow(retrieved));
//        //More extensive testing of WhatNow saving/reading is done in XmlWhatNowStorageTest
//    }
//
//    @Test
//    public void getWhatNowFilePath(){
//        assertNotNull(storageManager.getWhatNowFilePath());
//    }
//
//    @Test
//    public void handleWhatNowChangedEvent_exceptionThrown_eventRaised() throws IOException {
//        //Create a StorageManager while injecting a stub that throws an exception when the save method is called
//        Storage storage = new StorageManager(new XmlWhatNowStorageExceptionThrowingStub("dummy"), new JsonUserPrefsStorage("dummy"));
//        EventsCollector eventCollector = new EventsCollector();
//        storage.handleWhatNowChangedEvent(new WhatNowChangedEvent(new WhatNow()));
//        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
//    }
//
//
//    /**
//     * A Stub class to throw an exception when the save method is called
//     */
//    class XmlWhatNowStorageExceptionThrowingStub extends XmlWhatNowStorage{
//
//        public XmlWhatNowStorageExceptionThrowingStub(String filePath) {
//            super(filePath);
//        }
//
//        @Override
//        public void saveWhatNow(ReadOnlyWhatNow whatNow, String filePath) throws IOException {
//            throw new IOException("dummy exception");
//        }
//    }
//
//
//}
