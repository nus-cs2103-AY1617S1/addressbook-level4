package tars.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import tars.commons.exceptions.DataConversionException;
import tars.commons.util.FileUtil;
import tars.model.Tars;
import tars.model.ReadOnlyTars;
import tars.model.task.Task;
import tars.storage.XmlTarsStorage;
import tars.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlTarsStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTarsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTars_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTars(null);
    }

    private java.util.Optional<ReadOnlyTars> readTars(String filePath) throws Exception {
        return new XmlTarsStorage(filePath).readTars(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTars("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTars("NotXmlFormatTars.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveTars_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTars.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        Tars original = td.getTypicalTars();
        XmlTarsStorage xmlTarsStorage = new XmlTarsStorage(filePath);

        //Save in new file and read back
        xmlTarsStorage.saveTars(original, filePath);
        ReadOnlyTars readBack = xmlTarsStorage.readTars(filePath).get();
        assertEquals(original, new Tars(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.taskH));
        original.removeTask(new Task(TypicalTestTasks.taskA));
        xmlTarsStorage.saveTars(original, filePath);
        readBack = xmlTarsStorage.readTars(filePath).get();
        assertEquals(original, new Tars(readBack));

    }

    @Test
    public void saveTars_nullTars_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTars(null, "SomeFile.xml");
    }

    private void saveTars(ReadOnlyTars tars, String filePath) throws IOException {
        new XmlTarsStorage(filePath).saveTars(tars, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTars_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveTars(new Tars(), null);
    }


}
