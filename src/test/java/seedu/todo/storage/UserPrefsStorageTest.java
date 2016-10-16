package seedu.todo.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.FileUtil;
import seedu.todo.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserPrefsStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/UserPrefsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserPrefs_nullFilePath_assertionFailure() throws Exception {
        thrown.expect(AssertionError.class);
        readUserPrefs(null);
    }

    private UserPrefs readUserPrefs(String userPrefsFile) throws Exception {
        return new UserPrefsStorage(getPrefsPath(userPrefsFile)).read();
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws Exception {
        assertEquals(new UserPrefs(), readUserPrefs("NonExistentFile.json"));
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUserPrefs("NotJsonFormatUserPrefs.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String getPrefsPath(String filename) {
        return filename != null ? TEST_DATA_FOLDER + filename : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws Exception {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("TypicalUserPref.json");
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws Exception {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json");
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws Exception {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("ExtraValuesUserPref.json");

        assertEquals(expected, actual);
    }

    @Test
    public void savePrefs_nullPrefs_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    private void saveUserPrefs(UserPrefs userPrefs, String userPrefsFile) throws IOException {
        new UserPrefsStorage(getPrefsPath(userPrefsFile)).save(userPrefs);
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws Exception {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        UserPrefsStorage userPrefsStorage = new UserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        userPrefsStorage.save(original);
        UserPrefs readBack = userPrefsStorage.read();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        userPrefsStorage.save(original);
        readBack = userPrefsStorage.read();
        assertEquals(original, readBack);
    }

}
