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
import seedu.unburden.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskListStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskListStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskList_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskList(null);
    }

    private java.util.Optional<ReadOnlyListOfTask> readTaskList(String filePath) throws Exception {
        return new XmlTaskListStorage(filePath).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskList("NonExistentFile.xml").isPresent());
    }
/*
    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskList("NotXmlFormatAddressBook.xml");
        
        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         *
    }*/
/*
    @Test
    public void readAndSaveTaskList_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "tempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        ListOfTask original = td.getTypicalListOfTask();
        XmlTaskListStorage xmlTaskListStorage = new XmlTaskListStorage(filePath);

        //Save in new file and read back
        xmlTaskListStorage.saveTaskList(original, filePath);
        ReadOnlyListOfTask readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new ListOfTask(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removeTask(new Task(TypicalTestTasks.alice));
        xmlTaskListStorage.saveTaskList(original, filePath);
        readBack = xmlTaskListStorage.readTaskList(filePath).get();
        assertEquals(original, new ListOfTask(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.ida));
        xmlTaskListStorage.saveTaskList(original); //file path not specified
        readBack = xmlTaskListStorage.readTaskList().get(); //file path not specified
        assertEquals(original, new ListOfTask(readBack));

    }
*/
    @Test
    public void saveTaskList_nullTaskList_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(null, "SomeFile.xml");
    }

    private void saveTaskList(ReadOnlyListOfTask addressBook, String filePath) throws IOException {
        new XmlTaskListStorage(filePath).saveTaskList(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskList_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskList(new ListOfTask(), null);
    }


}
