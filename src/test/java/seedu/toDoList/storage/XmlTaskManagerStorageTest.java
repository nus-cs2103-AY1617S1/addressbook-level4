package seedu.toDoList.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.toDoList.commons.exceptions.DataConversionException;
import seedu.toDoList.commons.util.FileUtil;
import seedu.toDoList.model.ReadOnlyTaskManager;
import seedu.toDoList.model.TaskManager;
import seedu.toDoList.model.task.Task;
import seedu.toDoList.storage.FilePath;
import seedu.toDoList.storage.XmlTaskManagerStorage;
import seedu.toDoList.testutil.TypicalTestTasks;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XmlTaskManagerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskManager_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskManager(null);
    }

    private java.util.Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws Exception {
        return new XmlTaskManagerStorage(filePath).readTaskManager(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskManager("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskManager("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(filePath);

        //Save in new file and read back
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.project));
        original.removeTask(new Task(TypicalTestTasks.friend));
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.workshop));
        xmlTaskManagerStorage.saveTaskManager(original); //file path not specified
        readBack = xmlTaskManagerStorage.readTaskManager().get(); //file path not specified
        assertEquals(original, new TaskManager(readBack));

    }

    @Test
    public void saveTaskManager_nullTaskManager_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(null, "SomeFile.xml");
    }

    private void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        new XmlTaskManagerStorage(filePath).saveTaskManager(taskManager, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskManager_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(new TaskManager(), null);
    }
    
    //@@author A0146123R
    @Test
    public void setTaskManagerFilePath_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        setTaskManagerFilePath(null);
    }

    @Test
    public void setTaskManagerFilePath_validFilePath_success() throws IOException {
        assertTrue(setTaskManagerFilePath(new FilePath("SomeFile.xml", false)));
        assertFalse(setTaskManagerFilePath(new FilePath("SomeFile.xml", true)));
    }

    /**
     * Sets the task manager to the new file path.
     * @return whether the previous storage file exists
     */
    private boolean setTaskManagerFilePath(FilePath filePath) throws IOException {
        String defaultFilePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager taskManager = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(defaultFilePath);
        xmlTaskManagerStorage.saveTaskManager(taskManager, defaultFilePath);

        xmlTaskManagerStorage.setTaskManagerFilePath(filePath);
        assertEquals(filePath.getPath(), xmlTaskManagerStorage.getTaskManagerFilePath());
        return new File(defaultFilePath).exists();
    }

}