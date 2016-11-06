package w15c2.tusk.storage;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;
import org.junit.rules.*;

import w15c2.tusk.commons.collections.UniqueItemCollection;
import w15c2.tusk.commons.exceptions.DataConversionException;
import w15c2.tusk.commons.util.FileUtil;
import w15c2.tusk.model.Alias;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.model.task.TaskManager;
import w15c2.tusk.storage.alias.XmlAliasStorage;
import w15c2.tusk.storage.task.XmlTaskManagerStorage;
import w15c2.tusk.testutil.TestTask;
import w15c2.tusk.testutil.TypicalTestTasks;

public class XmlTaskManagerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskManagerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskManager_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskManager(null);
    }

    private java.util.Optional<UniqueItemCollection<Task>> readTaskManager(String filePath) throws Exception {
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

//    @Test
//    public void read_notXmlFormat_exceptionThrown() throws Exception {
//
//        thrown.expect(DataConversionException.class);
//        readTaskManager("NotXmlFormatTaskManager.xml");
//
//        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
//         * That means you should not have more than one exception test in one method
//         */
//    }

    @Test
    public void readAndSaveTaskManager_allInOrder_success() throws Exception {
        String taskManagerFilePath = testFolder.getRoot().getPath() + "TempTaskManager.xml";
        String aliasFilePath = testFolder.getRoot().getPath() + "TempAlias.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskManager original = td.getTypicalTaskManager();
        XmlTaskManagerStorage xmlTaskManagerStorage = new XmlTaskManagerStorage(taskManagerFilePath);
        XmlAliasStorage xmlAliasStorage = new XmlAliasStorage(aliasFilePath);


        //Save in new file and read back
        xmlTaskManagerStorage.saveTaskManager(original.getTasks(), taskManagerFilePath);
        xmlAliasStorage.saveAlias(original.getAliasCollection(), aliasFilePath);

        UniqueItemCollection<Task> taskReadBack = xmlTaskManagerStorage.readTaskManager(taskManagerFilePath).get();
        UniqueItemCollection<Alias> aliasReadBack = xmlAliasStorage.readAlias(aliasFilePath).get();

        assertEquals(original, new TaskManager(taskReadBack, aliasReadBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new TestTask(TypicalTestTasks.extraTask1));
        original.deleteTask(new TestTask(TypicalTestTasks.task2));
        original.addAlias(new Alias(TypicalTestTasks.extraAlias));
        original.deleteAlias(new Alias(TypicalTestTasks.alias1));

        xmlTaskManagerStorage.saveTaskManager(original.getTasks(), taskManagerFilePath);
        xmlAliasStorage.saveAlias(original.getAliasCollection(), aliasFilePath);
        taskReadBack = xmlTaskManagerStorage.readTaskManager(taskManagerFilePath).get();
        aliasReadBack = xmlAliasStorage.readAlias(aliasFilePath).get();

        assertEquals(original, new TaskManager(taskReadBack, aliasReadBack));

        //Save and read without specifying file path
        original.addTask(new TestTask(TypicalTestTasks.extraTask2));
        xmlTaskManagerStorage.saveTaskManager(original.getTasks()); //file path not specified
        taskReadBack = xmlTaskManagerStorage.readTaskManager().get(); //file path not specified
        assertEquals(original, new TaskManager(taskReadBack, aliasReadBack));

    }

    @Test
    public void saveTaskManager_nullTaskManager_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(null, "SomeFile.xml");
    }

    private void saveTaskManager(UniqueItemCollection<Task> addressBook, String filePath) throws IOException {
        new XmlTaskManagerStorage(filePath).saveTaskManager(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskManager_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskManager(new TaskManager().getTasks(), null);
    }


}
