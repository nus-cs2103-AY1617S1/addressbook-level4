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
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskBook_nullFilePath_assertionFailure() throws Exception {
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
        readTaskBook("NotXmlFormatTaskBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskBook original = td.getTypicalTaskBook();
        XmlTaskBookStorage xmlTaskBookStorage = new XmlTaskBookStorage(filePath);

        //Save in new file and read back
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        ReadOnlyTaskBook readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.todoExtra));
        original.removeTask(new Task(TypicalTestTasks.todo1));
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.eventExtra));
        xmlTaskBookStorage.saveTaskBook(original); //file path not specified
        readBack = xmlTaskBookStorage.readTaskBook().get(); //file path not specified
        assertEquals(original, new TaskBook(readBack));

    }

    @Test
    public void saveTaskBook_nullTaskBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBook(null, "SomeFile.xml");
    }

    private void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws IOException {
        new XmlTaskBookStorage(filePath).saveTaskBook(taskBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskBook(new TaskBook(), null);
    }


}
