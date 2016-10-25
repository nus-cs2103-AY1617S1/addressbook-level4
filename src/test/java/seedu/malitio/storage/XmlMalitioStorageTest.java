package seedu.malitio.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.malitio.testutil.TypicalTestTasks;
import seedu.malitio.commons.exceptions.DataConversionException;
import seedu.malitio.commons.util.FileUtil;
import seedu.malitio.model.Malitio;
import seedu.malitio.model.ReadOnlyMalitio;
import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.storage.XmlMalitioStorage;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlMalitioStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlMalitioStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readmalitio_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readMalitio(null);
    }

    private java.util.Optional<ReadOnlyMalitio> readMalitio(String filePath) throws Exception {
        return new XmlMalitioStorage(filePath).readMalitio(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMalitio("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readMalitio("NotXmlFormatMalitio.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSavemalitio_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "Tempmalitio.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        Malitio original = td.getTypicalMalitio();
        XmlMalitioStorage xmlmalitioStorage = new XmlMalitioStorage(filePath);

        //Save in new file and read back
        xmlmalitioStorage.saveMalitio(original, filePath);
        ReadOnlyMalitio readBack = xmlmalitioStorage.readMalitio(filePath).get();
        assertEquals(original, new Malitio(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addFloatingTask(new FloatingTask(TypicalTestTasks.manualFloatingTask1));
        original.removeTask(new FloatingTask(TypicalTestTasks.floatingTask1));
        xmlmalitioStorage.saveMalitio(original, filePath);
        readBack = xmlmalitioStorage.readMalitio(filePath).get();
        assertEquals(original, new Malitio(readBack));

        //Save and read without specifying file path
        original.addDeadline(new Deadline(TypicalTestTasks.manualDeadline1));
        xmlmalitioStorage.saveMalitio(original); //file path not specified
        readBack = xmlmalitioStorage.readMalitio().get(); //file path not specified
        assertEquals(original, new Malitio(readBack));

    }

    @Test
    public void savemalitio_nullmalitio_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        savemalitio(null, "SomeFile.xml");
    }

    private void savemalitio(ReadOnlyMalitio malitio, String filePath) throws IOException {
        new XmlMalitioStorage(filePath).saveMalitio(malitio, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void savemalitio_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        savemalitio(new Malitio(), null);
    }


}
