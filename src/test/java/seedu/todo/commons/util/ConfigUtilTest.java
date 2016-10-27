package seedu.todo.commons.util;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.ConfigDefinition;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.commons.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConfigUtilTest {

    private static final String TEST_DATA_FOLDER_PATH = "./src/test/data/ConfigUtilTest/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_null_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        read(null);
    }

    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {
        assertFalse(read("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJasonFormat_exceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        read("NotJasonFormatConfig.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void read_fileInOrder_successfullyRead() throws DataConversionException {

        Config expected = getTypicalConfig();

        Config actual = read("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void read_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        Config actual = read("EmptyConfig.json").get();
        assertEquals(new Config(), actual);
    }

    @Test
    public void read_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        Config expected = getTypicalConfig();
        Config actual = read("ExtraValuesConfig.json").get();

        assertEquals(expected, actual);
    }

    private Config getTypicalConfig() {
        Config config = new Config();
        config.setAppTitle("Typical App Title");
        config.setLogLevel(Level.INFO);
        config.setDatabaseFilePath("database.json");
        return config;
    }

    private List<String> getTypicalConfigNames() {
        return Arrays.asList("appTitle", "databaseFilePath");
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataConversionException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    @Test
    public void save_nullConfig_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(null, "SomeFile.json");
    }

    @Test
    public void save_nullFile_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(new Config(), null);
    }

    @Test
    public void saveConfig_allInOrder_success() throws DataConversionException, IOException {
        Config original = getTypicalConfig();

        String configFilePath = testFolder.getRoot() + File.separator + "TempConfig.json";

        //Try writing when the file doesn't exist
        ConfigUtil.saveConfig(original, configFilePath);
        Config readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setAppTitle("Updated Title");
        original.setLogLevel(Level.FINE);
        ConfigUtil.saveConfig(original, configFilePath);
        readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }
    
    @Test
    public void getDefinitions_configInOrder_configNamesCorrect() {
        List<String> expected = getTypicalConfigNames();
        List<ConfigDefinition> actualDefinitions = new Config().getDefinitions();
        List<String> actual = new ArrayList<>();
        
        for (ConfigDefinition defn : actualDefinitions) {
            actual.add(defn.getConfigName());
        }
        
        assertEquals(expected.size(), actual.size());
        
        for (String configName : expected) {
            boolean isInExpected = actual.indexOf(configName) >= 0;
            assertEquals(isInExpected, true);
        }
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        new ConfigUtil();
        ConfigUtil.saveConfig(config, configFilePath);
    }

    private String addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        String fullPath = FileUtil.getPath(TEST_DATA_FOLDER_PATH);
        
        return configFileInTestDataFolder != null
                                  ? fullPath + configFileInTestDataFolder
                                  : null;
    }


}
