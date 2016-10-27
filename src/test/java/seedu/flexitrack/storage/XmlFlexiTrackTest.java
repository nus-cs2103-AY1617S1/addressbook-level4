package seedu.flexitrack.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.commons.util.FileUtil;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.testutil.TypicalTestTasks;

public class XmlFlexiTrackTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlFlexiTrackStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyFlexiTrack> readAddressBook(String filePath) throws Exception {
        return new XmlFlexiTrackStorage(filePath).readFlexiTrack(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER + prefsFileInTestDataFolder : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatFlexiTrack.xml");

        /*
         * IMPORTANT: Any code below an exception-throwing line (like the one
         * above) will be ignored. That means you should not have more than one
         * exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempFlexiTrack.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        FlexiTrack original = td.getTypicalFlexiTrack();
        XmlFlexiTrackStorage xmlAddressBookStorage = new XmlFlexiTrackStorage(filePath);

        // Save in new file and read back
        xmlAddressBookStorage.saveFlexiTrack(original, filePath);
        ReadOnlyFlexiTrack readBack = xmlAddressBookStorage.readFlexiTrack(filePath).get();
        assertEquals(original, new FlexiTrack(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.basketball));
        original.removeTask(new Task(TypicalTestTasks.homework1));
        xmlAddressBookStorage.saveFlexiTrack(original, filePath);
        readBack = xmlAddressBookStorage.readFlexiTrack(filePath).get();
        assertEquals(original, new FlexiTrack(readBack));

        // Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.lecture));
        xmlAddressBookStorage.saveFlexiTrack(original); // file path not
                                                        // specified
        readBack = xmlAddressBookStorage.readFlexiTrack().get(); // file path
                                                                 // not
                                                                 // specified
        assertEquals(original, new FlexiTrack(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyFlexiTrack addressBook, String filePath) throws IOException {
        new XmlFlexiTrackStorage(filePath).saveFlexiTrack(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new FlexiTrack(), null);
    }

}
