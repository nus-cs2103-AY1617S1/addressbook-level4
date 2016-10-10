package seedu.unburden.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.unburden.commons.exceptions.DataConversionException;
import seedu.unburden.commons.util.FileUtil;
import seedu.unburden.model.ListOfTask;
import seedu.unburden.model.ReadOnlyListOfTask;
import seedu.unburden.model.task.Task;
import seedu.unburden.storage.XmlTaskListStorage;
import seedu.unburden.testutil.TypicalTestPersons;

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

    private java.util.Optional<ReadOnlyListOfTask> readAddressBook(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));
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
        TypicalTestPersons td = new TypicalTestPersons();
        ListOfTask original = td.getTypicalListOfTask();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyListOfTask readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new ListOfTask(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestPersons.hoon));
        original.removePerson(new Task(TypicalTestPersons.alice));
        xmlTaskListStorage.saveTaskList(original, filePath);
        readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new ListOfTask(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestPersons.ida));
        xmlTaskListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTaskListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new ListOfTask(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyListOfTask addressBook, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new ListOfTask(), null);
    }


}
