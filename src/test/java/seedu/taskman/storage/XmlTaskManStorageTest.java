package seedu.taskman.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.taskman.commons.exceptions.DataConversionException;
import seedu.taskman.commons.util.FileUtil;
import seedu.taskman.model.TaskMan;
import seedu.taskman.model.ReadOnlyTaskMan;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.Task;
import seedu.taskman.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTaskManStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTaskManStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskMan_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskMan(null);
    }

    private java.util.Optional<ReadOnlyTaskMan> readTaskMan(String filePath) throws Exception {
        return new XmlTaskManStorage(filePath).readTaskMan(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskMan("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTaskMan("NotXmlFormatTaskMan.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTaskMan_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskMan.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        TaskMan original = td.getTypicalTaskMan();
        XmlTaskManStorage xmlTaskManStorage = new XmlTaskManStorage(filePath);

        //Save in new file and read back
        xmlTaskManStorage.saveTaskMan(original, filePath);
        ReadOnlyTaskMan readBack = xmlTaskManStorage.readTaskMan(filePath).get();
        assertEquals(original, new TaskMan(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.taskCS2102));
        original.removeActivity(new Activity(new Task(TypicalTestTasks.taskCS2101)));
        xmlTaskManStorage.saveTaskMan(original, filePath);
        readBack = xmlTaskManStorage.readTaskMan(filePath).get();
        assertEquals(original, new TaskMan(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.taskCS2104));
        xmlTaskManStorage.saveTaskMan(original); //file path not specified
        readBack = xmlTaskManStorage.readTaskMan().get(); //file path not specified
        assertEquals(original, new TaskMan(readBack));

    }

    @Test
    public void saveTaskMan_nullTaskMan_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskMan(null, "SomeFile.xml");
    }

    private void saveTaskMan(ReadOnlyTaskMan taskMan, String filePath) throws IOException {
        new XmlTaskManStorage(filePath).saveTaskMan(taskMan, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTaskMan_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTaskMan(new TaskMan(), null);
    }


}
