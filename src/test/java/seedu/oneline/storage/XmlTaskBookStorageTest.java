package seedu.oneline.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.oneline.commons.exceptions.DataConversionException;
import seedu.oneline.commons.util.FileUtil;
import seedu.oneline.model.ReadOnlyTaskBook;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.task.Task;
import seedu.oneline.storage.XmlTaskBookStorage;
import seedu.oneline.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskBookStorageTest {
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

    private java.util.Optional<ReadOnlyTaskBook> readAddressBook(String filePath) throws Exception {
        return new XmlTaskBookStorage(filePath).readTaskBook(addToTestDataPathIfNotNull(filePath));
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
        readAddressBook("NotXmlFormatTaskBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskBook original = td.getTypicalTaskBook();
        XmlTaskBookStorage xmlAddressBookStorage = new XmlTaskBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveTaskBook(original, filePath);
        ReadOnlyTaskBook readBack = xmlAddressBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removeTask(new Task(TypicalTestTasks.alice));
        xmlAddressBookStorage.saveTaskBook(original, filePath);
        readBack = xmlAddressBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.ida));
        xmlAddressBookStorage.saveTaskBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readTaskBook().get(); //file path not specified
        assertEquals(original, new TaskBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyTaskBook addressBook, String filePath) throws IOException {
        new XmlTaskBookStorage(filePath).saveTaskBook(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new TaskBook(), null);
    }


}
