package seedu.agendum;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.agendum.commons.core.Config;
import seedu.agendum.commons.exceptions.DataConversionException;
import seedu.agendum.commons.util.ConfigUtil;
import seedu.agendum.logic.commands.CommandLibrary;
import seedu.agendum.model.UserPrefs;
import seedu.agendum.storage.JsonAliasTableStorage;
import seedu.agendum.storage.JsonUserPrefsStorage;
import seedu.agendum.storage.StorageManager;
import seedu.agendum.testutil.TestUtil;

//@@author A0148095X
public class MainAppTest {

    private MainApp mainApp;

    private Config defaultConfig;
    private UserPrefs defaultUserPrefs;
    private Hashtable<String, String> defaultAliasTable;
    
    // user prefs and alias table filepaths lead to empty files
    private Config configWithBadFilePaths; 
    // user prefs and alias table filepaths lead to read only files
    private Config configWithReadOnlyFilePaths;

    private final String pathToBadConfig = TestUtil.getFilePathInSandboxFolder("bad_config.json");
    private final String pathToReadOnlyConfig = TestUtil.getFilePathInSandboxFolder("read_only_config.json");

    private final String pathToBadUserPrefs = TestUtil.getFilePathInSandboxFolder("bad_user_prefs.json");
    private final String pathToReadOnlyUserPrefs = TestUtil.getFilePathInSandboxFolder("read_only_user_prefs.json");

    private final String pathToBadAliasTable = TestUtil.getFilePathInSandboxFolder("bad_alias_table.json");
    private final String pathToReadOnlyAliasTable = TestUtil.getFilePathInSandboxFolder("read_only_alias_table.json");

    @Before
    public void setUp() {
        mainApp = new MainApp();

        defaultConfig = new Config();
        defaultUserPrefs = new UserPrefs();
        defaultAliasTable = new Hashtable<String, String>();

        configWithBadFilePaths = generateConfigWithBadFilePaths();
        configWithReadOnlyFilePaths = generateConfigWithReadOnlyFilePaths();

        createEmptyFile(pathToBadConfig);
        createReadOnlyConfigFile(pathToReadOnlyConfig);

        createEmptyFile(pathToBadUserPrefs);
        createReadOnlyUserPrefsFile(pathToReadOnlyUserPrefs);

        createEmptyFile(pathToBadAliasTable);
        createReadOnlyAliasTableFile(pathToReadOnlyAliasTable);
    }

    @Test
    public void initConfig_nullFilePath_returnsDefaultConfig() {
        Config config = mainApp.initConfig(null);
        assertEquals(config, defaultConfig);
    }

    @Test
    public void initConfig_validFilePathInvalidFileFormat_returnsDefaultConfig() {
        Config config = mainApp.initConfig(pathToBadConfig);
        assertEquals(config, defaultConfig);
    }

    @Test
    public void initConfig_validFilePathValidFormatReadOnly_returnsDefaultConfigLogsWarning() {
        Config config = mainApp.initConfig(pathToReadOnlyConfig);
        assertEquals(config, defaultConfig);
    }

    @Test
    public void initPrefs_invalidFileFormat_returnsDefaultUserPrefs() {
        // Set up storage to point to bad user prefs
        mainApp.storage = new StorageManager("", "", pathToBadUserPrefs, null);

        UserPrefs userPrefs = mainApp.initPrefs(configWithBadFilePaths);
        assertEquals(userPrefs, defaultUserPrefs);

        // reset storage
        mainApp.storage = null;
    }

    @Test
    public void initPrefs_validFileReadOnly_returnsDefaultUserPrefsLogsWarning() {
        // Set up storage to point to read only prefs
        mainApp.storage = new StorageManager("", "", pathToReadOnlyUserPrefs, null);

        UserPrefs userPrefs = mainApp.initPrefs(configWithReadOnlyFilePaths);
        assertEquals(userPrefs, defaultUserPrefs);

        // reset storage
        mainApp.storage = null;
    }

    @Test
    public void initPrefs_exceptionThrowingStorage_returnsDefaultUserPrefsLogsWarning() {
        mainApp.storage = new ReadUserPrefsExceptionThrowingStorageManagerStub();

        UserPrefs userPrefs = mainApp.initPrefs(defaultConfig);
        assertEquals(userPrefs, defaultUserPrefs);

        // reset storage
        mainApp.storage = null;
    }

    @Test
    public void initAliasTable_invalidFileFormat_returnsEmptyHashtable() {
        // Set up storage to point to bad alias table
        mainApp.storage = new StorageManager("", pathToBadAliasTable, "", null);

        mainApp.initAliasTable(configWithBadFilePaths);
        Hashtable<String, String> actualAliasTable = CommandLibrary.getInstance().getAliasTable();
        assertEquals(actualAliasTable, defaultAliasTable);

        // reset storage and alias table
        mainApp.storage = null;
        CommandLibrary.getInstance().loadAliasTable(null);
    }

    @Test
    public void initAliasTable_validFileFormatReadOnly_returnsEmptyHashtable() {
        // Set up storage to point to read only alias table
        mainApp.storage = new StorageManager("", pathToReadOnlyAliasTable, "", null);

        mainApp.initAliasTable(configWithReadOnlyFilePaths);
        Hashtable<String, String> actualAliasTable = CommandLibrary.getInstance().getAliasTable();
        assertEquals(actualAliasTable, defaultAliasTable);

        // reset storage and alias table
        mainApp.storage = null;
        CommandLibrary.getInstance().loadAliasTable(null);
    }

    @Test
    public void initAliasTable_exceptionThrowingStorage_returnsDefaultHashtableLogsWarning() {
        mainApp.storage = new ReadAliasTableExceptionThrowingStorageManagerStub();

        mainApp.initAliasTable(defaultConfig);
        Hashtable<String, String> actualAliasTable = CommandLibrary.getInstance().getAliasTable();
        assertEquals(actualAliasTable, defaultAliasTable);

        // reset storage and alias table
        mainApp.storage = null;
        CommandLibrary.getInstance().loadAliasTable(null);
    }
    
    private void createEmptyFile(String filePath) {
        File file = new File(filePath);

        deleteIfExists(file);

        try {
            file.createNewFile();
        } catch (IOException e) {
            Assert.fail("Error creating empty file at: " + filePath);
        }
    }

    private void createReadOnlyConfigFile(String filePath) {
        File file = new File(filePath);

        // Ensure that the file is empty
        deleteIfExists(file);

        try {
            ConfigUtil.saveConfig(defaultConfig, filePath);
        } catch (IOException e) {
            Assert.fail("Error creating read only config file");
        }

        if (!file.setReadOnly()) {
            Assert.fail("Unable to set read only config to read only");
        }
    }

    private void createReadOnlyUserPrefsFile(String filePath) {
        File file = new File(filePath);

        // Ensure that the file is empty
        deleteIfExists(file);

        try {
            JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(filePath);
            userPrefsStorage.saveUserPrefs(defaultUserPrefs, filePath);
        } catch (IOException e) {
            Assert.fail("Error creating read only user prefs file");
        }

        if (!file.setReadOnly()) {
            Assert.fail("Unable to set read only user prefs to read only");
        }
    }

    private void createReadOnlyAliasTableFile(String filePath) {
        File file = new File(filePath);

        // Ensure that the file is empty
        deleteIfExists(file);

        try {
            JsonAliasTableStorage aliasTableStorage = new JsonAliasTableStorage(filePath);
            aliasTableStorage.saveAliasTable(defaultAliasTable, filePath);
        } catch (IOException e) {
            Assert.fail("Error creating read only alias table file");
        }

        if (!file.setReadOnly()) {
            Assert.fail("Unable to set read only alias table to read only");
        }
    }

    public void deleteIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    private Config generateConfigWithReadOnlyFilePaths() {
        Config config = new Config();
        config.setAliasTableFilePath(pathToReadOnlyAliasTable);
        config.setUserPrefsFilePath(pathToReadOnlyUserPrefs);
        return config;
    }

    private Config generateConfigWithBadFilePaths() {
        Config config = new Config();
        config.setAliasTableFilePath(pathToBadAliasTable);
        config.setUserPrefsFilePath(pathToBadUserPrefs);
        return config;        
    }

    /** Throws an IOException when readUserPrefs is called **/
    class ReadUserPrefsExceptionThrowingStorageManagerStub extends StorageManager {
        public ReadUserPrefsExceptionThrowingStorageManagerStub() {
            super("", "", "", null);
        }

        public Optional<UserPrefs> readUserPrefs() throws IOException {
            throw new IOException(this.getClass().getCanonicalName() +": IOException");
        }
    }    

    /** Throws an IOException when readAliasTable is called **/
    class ReadAliasTableExceptionThrowingStorageManagerStub extends StorageManager {
        public ReadAliasTableExceptionThrowingStorageManagerStub() {
            super("", "", "", null);
        }

        @Override
        public Optional<Hashtable<String, String>> readAliasTable() throws DataConversionException, IOException {
            throw new IOException(this.getClass().getCanonicalName() + ": IOException");
        }
    }
}
