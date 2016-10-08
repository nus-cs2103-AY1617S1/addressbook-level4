package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ActivityManager;
import seedu.address.model.ReadOnlyActivityManager;
import seedu.address.model.activity.Activity;
import seedu.address.testutil.TypicalTestActivities;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlAddressBookStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyActivityManager> readAddressBook(String filePath) throws Exception {
        return new XmlActivityManagerStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestActivities td = new TypicalTestActivities();
        ActivityManager original = td.getTypicalActivityManager();
        XmlActivityManagerStorage xmlActivityManagerStorage = new XmlActivityManagerStorage(filePath);

        //Save in new file and read back
        xmlActivityManagerStorage.saveAddressBook(original, filePath);
        ReadOnlyActivityManager readBack = xmlActivityManagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addActivity(new Activity(TypicalTestActivities.hoon));
        original.removeActivity(new Activity(TypicalTestActivities.alice));
        xmlActivityManagerStorage.saveAddressBook(original, filePath);
        readBack = xmlActivityManagerStorage.readAddressBook(filePath).get();
        assertEquals(original, new ActivityManager(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyActivityManager addressBook, String filePath) throws IOException {
        new XmlActivityManagerStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new ActivityManager(), null);
    }


}
