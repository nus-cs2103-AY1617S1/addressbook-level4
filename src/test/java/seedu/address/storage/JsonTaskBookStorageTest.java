package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;
import seedu.address.testutil.TypicalTestTasks;

public class JsonTaskBookStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/JsonTaskBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws Exception {
        final JsonTaskBookStorage storage = new JsonTaskBookStorage(filePath);
        return storage.readTaskBook(filePath == null ? null : TEST_DATA_FOLDER + filePath);
    }

    @Test
    public void readTaskBook_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readTaskBook(null);
    }

    @Test
    public void readTaskBook_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void readTaskBook_notJsonFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("NotJsonFormatTaskBook.json");
    }

    @Test
    public void readAndSaveTaskBook_allInOrder_success() throws Exception {
        final String filePath = testFolder.getRoot().getPath() + "TempTaskBook.json";
        final TypicalTestTasks tt = new TypicalTestTasks();
        final TaskBook original = tt.getTypicalAddressBook();
        final JsonTaskBookStorage storage = new JsonTaskBookStorage(filePath);

        // Save in new file and read back
        storage.saveTaskBook(original);
        final ReadOnlyTaskBook readBack = storage.readTaskBook().get();
        assertEquals(original, readBack);
    }
}
