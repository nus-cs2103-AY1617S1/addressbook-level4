package seedu.savvytasker.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.commons.util.FileUtil;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.task.Task;
import seedu.savvytasker.storage.XmlSavvyTaskerStorage;
import seedu.savvytasker.testutil.TypicalTestTasks;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlAddressBookStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readSavvyTasker_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readSavvyTasker(null);
    }

    private java.util.Optional<ReadOnlySavvyTasker> readSavvyTasker(String filePath) throws Exception {
        return new XmlSavvyTaskerStorage(filePath).readSavvyTasker(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSavvyTasker("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readSavvyTasker("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTasks td = new TypicalTestTasks();
        SavvyTasker original = td.getTypicalSavvyTasker();
        XmlSavvyTaskerStorage xmlSavvyTaskerStorage = new XmlSavvyTaskerStorage(filePath);

        //Save in new file and read back
        xmlSavvyTaskerStorage.saveSavvyTasker(original, filePath);
        ReadOnlySavvyTasker readBack = xmlSavvyTaskerStorage.readSavvyTasker(filePath).get();
        assertEquals(original, new SavvyTasker(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.happy));
        original.removeTask(new Task(td.happy));
        xmlSavvyTaskerStorage.saveSavvyTasker(original, filePath);
        readBack = xmlSavvyTaskerStorage.readSavvyTasker(filePath).get();
        assertEquals(original, new SavvyTasker(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.happy));
        xmlSavvyTaskerStorage.saveSavvyTasker(original); //file path not specified
        readBack = xmlSavvyTaskerStorage.readSavvyTasker().get(); //file path not specified
        assertEquals(original, new SavvyTasker(readBack));

    }

    @Test
    public void saveSavvyTasker_nullSavvyTasker_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveSavvyTasker(null, "SomeFile.xml");
    }

    private void saveSavvyTasker(ReadOnlySavvyTasker addressBook, String filePath) throws IOException {
        new XmlSavvyTaskerStorage(filePath).saveSavvyTasker(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveSavvyTasker_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveSavvyTasker(new SavvyTasker(), null);
    }


}
