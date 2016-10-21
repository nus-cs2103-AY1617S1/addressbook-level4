package seedu.taskscheduler.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.taskscheduler.commons.exceptions.DataConversionException;
import seedu.taskscheduler.commons.util.FileUtil;
import seedu.taskscheduler.model.ReadOnlyTaskScheduler;
import seedu.taskscheduler.model.TaskScheduler;
import seedu.taskscheduler.model.task.Task;
import seedu.taskscheduler.storage.XmlTaskSchedulerStorage;
import seedu.taskscheduler.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskSchedulerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskSchedulerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskScheduler(null);
    }

    private java.util.Optional<ReadOnlyTaskScheduler> readTaskScheduler(String filePath) throws Exception {
        return new XmlTaskSchedulerStorage(filePath).readTaskScheduler(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskScheduler("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskScheduler("NotXmlFormatTaskScheduler.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskScheduler_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskScheduler.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskScheduler original = td.getTypicalTaskScheduler();
        XmlTaskSchedulerStorage xmlTaskSchedulerStorage = new XmlTaskSchedulerStorage(filePath);

        //Save in new file and read back
        xmlTaskSchedulerStorage.saveTaskScheduler(original, filePath);
        ReadOnlyTaskScheduler readBack = xmlTaskSchedulerStorage.readTaskScheduler(filePath).get();
        assertEquals(original, new TaskScheduler(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.hoon));
        original.removeTask(new Task(TypicalTestTasks.alice));
        xmlTaskSchedulerStorage.saveTaskScheduler(original, filePath);
        readBack = xmlTaskSchedulerStorage.readTaskScheduler(filePath).get();
        assertEquals(original, new TaskScheduler(readBack));

    }

    @Test
    public void saveTaskScheduler_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskScheduler(null, "SomeFile.xml");
    }

    private void saveTaskScheduler(ReadOnlyTaskScheduler taskScheduler, String filePath) throws IOException {
        new XmlTaskSchedulerStorage(filePath).saveTaskScheduler(taskScheduler, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskScheduler_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskScheduler(new TaskScheduler(), null);
    }


}
