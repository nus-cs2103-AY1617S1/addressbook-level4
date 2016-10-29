package seedu.agendum.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.FileUtil;
import seedu.agendum.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JsonUserPrefsStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonUserPrefsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserPrefsNullFilePathSssertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readUserPrefs(null);
    }

    private Optional<UserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder) throws DataConversionException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefsMissingFileEmptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserPrefsNotJasonFormatExceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        readUserPrefs("NotJsonFormatUserPrefs.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userPrefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readUserPrefsFileInOrderSuccessfullyRead() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("TypicalUserPref.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefsValuesMissingFromFileDefaultValuesUsed() throws DataConversionException {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json").get();
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void readUserPrefsExtraValuesInFileExtraValuesIgnored() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("ExtraValuesUserPref.json").get();

        assertEquals(expected, actual);
    }

    @Test
    public void savePrefsNullPrefsAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefsNullFilePathAssertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) throws IOException {
        new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder)).saveUserPrefs(userPrefs);
    }

    @Test
    public void saveUserPrefsAllInOrderSuccess() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

}
