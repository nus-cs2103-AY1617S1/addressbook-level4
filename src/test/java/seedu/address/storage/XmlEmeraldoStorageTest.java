package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.testutil.TypicalTestTasks;
import seedu.emeraldo.commons.exceptions.DataConversionException;
import seedu.emeraldo.commons.util.FileUtil;
import seedu.emeraldo.model.Emeraldo;
import seedu.emeraldo.model.ReadOnlyEmeraldo;
import seedu.emeraldo.model.task.Task;
import seedu.emeraldo.storage.XmlEmeraldoStorage;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlEmeraldoStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlEmeraldoStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEmeraldo_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readEmeraldo(null);
    }

    private java.util.Optional<ReadOnlyEmeraldo> readEmeraldo(String filePath) throws Exception {
        return new XmlEmeraldoStorage(filePath).readEmeraldo(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEmeraldo("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEmeraldo("NotXmlFormatEmeraldo.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveEmeraldo_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEmeraldo.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        Emeraldo original = td.getTypicalEmeraldo();
        XmlEmeraldoStorage xmlEmeraldoStorage = new XmlEmeraldoStorage(filePath);

        //Save in new file and read back
        xmlEmeraldoStorage.saveEmeraldo(original, filePath);
        ReadOnlyEmeraldo readBack = xmlEmeraldoStorage.readEmeraldo(filePath).get();
        assertEquals(original, new Emeraldo(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTasks.homework));
        original.removeTask(new Task(TypicalTestTasks.application));
        xmlEmeraldoStorage.saveEmeraldo(original, filePath);
        readBack = xmlEmeraldoStorage.readEmeraldo(filePath).get();
        assertEquals(original, new Emeraldo(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTasks.groceries));
        xmlEmeraldoStorage.saveEmeraldo(original); //file path not specified
        readBack = xmlEmeraldoStorage.readEmeraldo().get(); //file path not specified
        assertEquals(original, new Emeraldo(readBack));

    }

    @Test
    public void saveEmeraldo_nullEmeraldo_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveEmeraldo(null, "SomeFile.xml");
    }

    private void saveEmeraldo(ReadOnlyEmeraldo emeraldo, String filePath) throws IOException {
        new XmlEmeraldoStorage(filePath).saveEmeraldo(emeraldo, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveEmeraldo_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveEmeraldo(new Emeraldo(), null);
    }


}
