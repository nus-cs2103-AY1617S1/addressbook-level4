package seedu.testplanner.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.dailyplanner.commons.exceptions.DataConversionException;
import seedu.dailyplanner.commons.util.FileUtil;
import seedu.dailyplanner.model.DailyPlanner;
import seedu.dailyplanner.model.ReadOnlyDailyPlanner;
import seedu.dailyplanner.model.task.Task;
import seedu.dailyplanner.storage.XmlDailyPlannerStorage;
import seedu.testplanner.testutil.TypicalTestTask;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlDailyPlannerStorageTest {
    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyDailyPlanner> readAddressBook(String filePath) throws Exception {
        return new XmlDailyPlannerStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTestTask td = new TypicalTestTask();
        DailyPlanner original = td.getTypicalAddressBook();
        XmlDailyPlannerStorage xmlDailyPlannerStorage = new XmlDailyPlannerStorage(filePath);

        //Save in new file and read back
        xmlDailyPlannerStorage.saveAddressBook(original, filePath);
        ReadOnlyDailyPlanner readBack = xmlDailyPlannerStorage.readAddressBook(filePath).get();
        assertEquals(original, new DailyPlanner(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(TypicalTestTask.learnSpanish));
        original.removeTask(new Task(TypicalTestTask.WatchMovie));
        xmlDailyPlannerStorage.saveAddressBook(original, filePath);
        readBack = xmlDailyPlannerStorage.readAddressBook(filePath).get();
        assertEquals(original, new DailyPlanner(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(TypicalTestTask.GoSkydiving));
        xmlDailyPlannerStorage.saveAddressBook(original); //file path not specified
        readBack = xmlDailyPlannerStorage.readDailyPlanner().get(); //file path not specified
        assertEquals(original, new DailyPlanner(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyDailyPlanner addressBook, String filePath) throws IOException {
        new XmlDailyPlannerStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveAddressBook(new DailyPlanner(), null);
    }


}
