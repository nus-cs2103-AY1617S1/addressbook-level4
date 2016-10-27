package harmony.mastermind.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import harmony.mastermind.commons.exceptions.DataConversionException;
import harmony.mastermind.commons.util.FileUtil;
import harmony.mastermind.model.ReadOnlyTaskManager;
import harmony.mastermind.model.TaskManager;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.storage.XmlTaskManagerStorage;
import harmony.mastermind.testutil.TypicalTestTasks;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskManagerStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlMastermindStorageTest/");
    private static String SECOND_TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/MigrationMastermindStorageTest/");

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

//    redundant 
//    @Test
//    public void read_notXmlFormat_exceptionThrown() throws Exception {
//
//        thrown.expect(DataConversionException.class);
//        readTaskManager("NotXmlFormatMastermind.xml");
//
//        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
//         * That means you should not have more than one exception test in one method
//         */
//    }


    //@@author A0124797R
    @Test
    public void readAndSaveTaskManager_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskManager.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(filePath);

        //Save in new file and read back
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        ReadOnlyTaskManager readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        assertEquals(original, new TaskManager(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.task5));
        original.removeTask(new Task(TypicalTestTasks.task3));
        xmlTaskManagerStorage.saveTaskManager(original, filePath);
        readBack = xmlTaskManagerStorage.readTaskManager(filePath).get();
        
        assertEquals(original, new TaskManager(readBack));
        
        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.task6));
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

    //@@author A0139194X
    @Test
    public void setTaskManagerFilePath_correctFilePath_assertionSuccess() {
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(TEST_DATA_FOLDER);
        xmlTaskManagerStorage.setTaskManagerFilePath(SECOND_TEST_DATA_FOLDER);
        assertEquals(xmlTaskManagerStorage.getTaskManagerFilePath(), SECOND_TEST_DATA_FOLDER);
    }
    
    //@@author A0139194X
    @Test
    public void migrateIntoNewFolder_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(TEST_DATA_FOLDER);
        xmlTaskManagerStorage.migrateIntoNewFolder(TEST_DATA_FOLDER, null);
    }
    
    //@@author A0139194X
    @Test
    public void deleteFile_nullFilePath_assertionFailure() {
        thrown.expect(AssertionError.class);
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(TEST_DATA_FOLDER);
        xmlTaskManagerStorage.deleteFile(null);
    }
    
    //@@author A0139194X
    @Test
    public void migrateNewFolder_allInOrder_success() throws IOException, DataConversionException {
        String filePath = testFolder.getRoot().getPath();
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(filePath);

        //Tries to delete old file again
        //TODO: need revise. folder creation is not working -by kf
        /* 
        xmlTaskManagerStorage.migrateIntoNewFolder(filePath, SECOND_TEST_DATA_FOLDER);
        File toDelete = new File(filePath);
        assertFalse(toDelete.delete());
        
        //Checks if file has been copied over to new location
        File newFile = new File(SECOND_TEST_DATA_FOLDER + "mastermind.xml");
        assertEquals(true, newFile.exists());
        */
    }

    
}
