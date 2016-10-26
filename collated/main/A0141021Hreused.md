# A0141021Hreused
###### \src\main\java\seedu\whatnow\commons\core\Config.java
``` java
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
###### \src\main\java\seedu\whatnow\commons\core\Messages.java
``` java
/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_TASK_LISTED_OVERVIEW = "%1$d tasks listed!";
    public static final String MESSAGE_INVALID_PATH = "Invalid Path!"; 

}
```
###### \src\main\java\seedu\whatnow\commons\util\CollectionUtil.java
``` java
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /**
     * Returns true if any of the given items are null.
     */
    public static boolean isAnyNull(Object... items) {
        for (Object item : items) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }



    /**
     * Throws an assertion error if the collection or any item in it is null.
     */
    public static void assertNoNullElements(Collection<?> items) {
        assert items != null;
        assert !isAnyNull(items);
    }

    /**
     * Returns true if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }
}
```
###### \src\main\java\seedu\whatnow\commons\util\JsonUtil.java
``` java
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
###### \src\main\java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate that a task was added */
    private void indicateAddTask(Task task) {
        raise (new AddTaskEvent(task));
    }
```
###### \src\main\java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate that a task was updated */
    private void indicateUpdateTask(Task task) {
        raise (new UpdateTaskEvent(task));
    }
```
###### \src\main\java\seedu\whatnow\storage\Storage.java
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
