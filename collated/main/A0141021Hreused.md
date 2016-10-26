# A0141021Hreused
###### \java\seedu\whatnow\commons\core\Config.java
``` java
package seedu.whatnow.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "WhatNow App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String whatNowFilePath = "data/whatnow.xml";
    private String whatNowName = "MyWhatNow";


    public Config() {
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getWhatNowFilePath() {
        return whatNowFilePath;
    }

    public void setWhatNowFilePath(String whatNowFilePath) {
        this.whatNowFilePath = whatNowFilePath;
    }

    public String getWhatNowName() {
        return whatNowName;
    }

    public void setWhatNowName(String whatNowName) {
        this.whatNowName = whatNowName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Config)){ //this handles null as well.
            return false;
        }

        Config o = (Config)other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(whatNowFilePath, o.whatNowFilePath)
                && Objects.equals(whatNowName, o.whatNowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, whatNowFilePath, whatNowName);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + whatNowFilePath);
        sb.append("\nWhatNow name : " + whatNowName);
        return sb.toString();
    }

}
```
###### \java\seedu\whatnow\commons\events\ui\ShowHelpRequestEvent.java
``` java
package seedu.whatnow.commons.events.ui;

import seedu.whatnow.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\whatnow\commons\util\ConfigUtil.java
``` java
package seedu.whatnow.commons.util;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    /**
     * Returns the Config object from the given file or {@code Optional.empty()} object if the file is not found.
     *   If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param configFilePath cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {

        assert configFilePath != null;

        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            logger.info("Config file "  + configFile + " not found");
            return Optional.empty();
        }

        Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warning("Error reading from config file " + configFile + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(config);
    }

    /**
     * Saves the Config object to the specified file.
     *   Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param config cannot be null
     * @param configFilePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static void saveConfig(Config config, String configFilePath) throws IOException {
        assert config != null;
        assert configFilePath != null;

        FileUtil.serializeObjectToJsonFile(new File(configFilePath), config);
    }

}
```
###### \java\seedu\whatnow\commons\util\FileUtil.java
``` java
package seedu.whatnow.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Writes and reads file
 */
public class FileUtil {
    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(File file) {
        return file.exists() && file.isFile();
    }

    public static void createIfMissing(File file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories
     *
     * @return true if file is created, false if file already exists
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            return false;
        }

        createParentDirsOfFile(file);

        return file.createNewFile();
    }

    /**
     * Creates the given directory along with its parent directories
     *
     * @param dir the directory to be created; assumed not null
     * @throws IOException if the directory or a parent directory cannot be created
     */
    public static void createDirs(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to make directories of " + dir.getName());
        }
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(File file) throws IOException {
        File parentDir = file.getParentFile();

        if (parentDir != null) {
            createDirs(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(File file, String content) throws IOException {
        Files.write(file.toPath(), content.getBytes(CHARSET));
    }

    /**
     * Converts a string to a platform-specific file path
     * @param pathWithForwardSlash A String representing a file path but using '/' as the separator
     * @return {@code pathWithForwardSlash} but '/' replaced with {@code File.separator}
     */
    public static String getPath(String pathWithForwardSlash) {
        assert pathWithForwardSlash != null;
        assert pathWithForwardSlash.contains("/");
        return pathWithForwardSlash.replace("/", File.separator);
    }

    public static <T> void serializeObjectToJsonFile(File jsonFile, T objectToSerialize) throws IOException {
        FileUtil.writeToFile(jsonFile, JsonUtil.toJsonString(objectToSerialize));
    }

    public static <T> T deserializeObjectFromJsonFile(File jsonFile, Class<T> classOfObjectToDeserialize)
            throws IOException {
        return JsonUtil.fromJsonString(FileUtil.readFromFile(jsonFile), classOfObjectToDeserialize);
    }
}
```
###### \java\seedu\whatnow\commons\util\JsonUtil.java
``` java
package seedu.whatnow.commons.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Converts a Java object instance to JSON and vice versa
 */
public class JsonUtil {
    private static class LevelDeserializer extends FromStringDeserializer<Level> {

        protected LevelDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        protected Level _deserialize(String value, DeserializationContext ctxt) throws IOException {
            return getLoggingLevel(value);
        }

        /**
         * Gets the logging level that matches loggingLevelString
         * <p>
         * Returns null if there are no matches
         *
         * @param loggingLevelString
         * @return
         */
        private Level getLoggingLevel(String loggingLevelString) {
            return Level.parse(loggingLevelString);
        }

        @Override
        public Class<Level> handledType() {
            return Level.class;
        }
    }

    private static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .registerModule(new SimpleModule("SimpleModule")
                    .addSerializer(Level.class, new ToStringSerializer())
                    .addDeserializer(Level.class, new LevelDeserializer(Level.class)));

    /**
     * Converts a given string representation of a JSON data to instance of a class
     * @param <T> The generic type to create an instance of
     * @return The instance of T with the specified values in the JSON string
     */
    public static <T> T fromJsonString(String json, Class<T> instanceClass) throws IOException {
        return objectMapper.readValue(json, instanceClass);
    }

    /**
     * Converts a given instance of a class into its JSON data string representation
     * @param instance The T object to be converted into the JSON string
     * @param <T> The generic type to create an instance of
     * @return JSON data representation of the given class instance, in string
     */
    public static <T> String toJsonString(T instance) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(instance);
    }

}
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    public ModelManager() {
        this(new WhatNow(), new UserPrefs());
    }
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate that a task was added */
    private void indicateAddTask(Task task) {
        raise (new AddTaskEvent(task));
    }
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate that a task was updated */
    private void indicateUpdateTask(Task task) {
        raise (new UpdateTaskEvent(task));
    }
```
###### \java\seedu\whatnow\storage\Storage.java
``` java
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.events.model.ConfigChangedEvent;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WhatNowStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getWhatNowFilePath();

    @Override
    Optional<ReadOnlyWhatNow> readWhatNow() throws DataConversionException, IOException;

    @Override
    void saveWhatNow(ReadOnlyWhatNow whatNow) throws IOException;

    /**
     * Saves the current version of the WhatNow to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleWhatNowChangedEvent(WhatNowChangedEvent abce);
    
    void handleFileLocationChangedEvent(ConfigChangedEvent dfgh);

    void saveConfig(Config config) throws IOException;

    void saveConfig(Config config, String filePath) throws IOException;
}
```
