package seedu.jimi.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.jimi.commons.exceptions.DataConversionException;
import seedu.jimi.commons.util.FileUtil;
import seedu.jimi.model.TaskBook;
import seedu.jimi.model.ReadOnlyTaskBook;
import seedu.jimi.model.task.FloatingTask;
import seedu.jimi.storage.XmlTaskBookStorage;
import seedu.jimi.testutil.TypicalTestFloatingTasks;

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
        readTaskBook(null);
    }

    private java.util.Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws Exception {
        return new XmlTaskBookStorage(filePath).readTaskBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestFloatingTasks td = new TypicalTestFloatingTasks();
        TaskBook original = td.getTypicalTaskBook();
        XmlTaskBookStorage xmlTaskBookStorage = new XmlTaskBookStorage(filePath);

        //Save in new file and read back
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        ReadOnlyTaskBook readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new FloatingTask(TypicalTestFloatingTasks.night));
        original.removeTask(new FloatingTask(TypicalTestFloatingTasks.water));
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Save and read without specifying file path
        original.addTask(new FloatingTask(TypicalTestFloatingTasks.dream));
        xmlTaskBookStorage.saveTaskBook(original); //file path not specified
        readBack = xmlTaskBookStorage.readTaskBook().get(); //file path not specified
        assertEquals(original, new TaskBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBook(null, "SomeFile.xml");
    }

    private void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        new XmlTaskBookStorage(filePath).saveTaskBook(taskBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBook(new TaskBook(), null);
    }


}
