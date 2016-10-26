# A0139772Ureused
###### \java\seedu\whatnow\commons\core\ComponentManager.java
``` java
package seedu.whatnow.commons.core;

import seedu.whatnow.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public abstract class ComponentManager {
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public ComponentManager(){
        this(EventsCenter.getInstance());
    }

    public ComponentManager(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event){
        eventsCenter.post(event);
    }
}
```
###### \java\seedu\whatnow\commons\core\EventsCenter.java
``` java
package seedu.whatnow.commons.core;

import com.google.common.eventbus.EventBus;

import seedu.whatnow.commons.events.BaseEvent;

import java.util.logging.Logger;

/**
 * Manages the event dispatching of the app.
 */
public class EventsCenter {
    private static final Logger logger = LogsCenter.getLogger(EventsCenter.class);
    private final EventBus eventBus;
    private static EventsCenter instance;

    public static EventsCenter getInstance() {
        if (instance == null) {
            instance = new EventsCenter();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    private EventsCenter() {
        eventBus = new EventBus();
    }

    public EventsCenter registerHandler(Object handler) {
        eventBus.register(handler);
        return this;
    }

    /**
     * Posts an event to the event bus.
     */
    public <E extends BaseEvent> EventsCenter post(E event) {
        logger.info("------[Event Posted] " + event.getClass().getCanonicalName() + ": " + event.toString());
        eventBus.post(event);
        return this;
    }

}
```
###### \java\seedu\whatnow\commons\core\GuiSettings.java
``` java
package seedu.whatnow.commons.core;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private Double windowWidth;
    private Double windowHeight;
    private Point windowCoordinates;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(Double windowWidth, Double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public Double getWindowWidth() {
        return windowWidth;
    }

    public Double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof GuiSettings)){ //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings)other;

        return Objects.equals(windowWidth, o.windowWidth)
                && Objects.equals(windowHeight, o.windowHeight)
                && Objects.equals(windowCoordinates.x, o.windowCoordinates.x)
                && Objects.equals(windowCoordinates.y, o.windowCoordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        return sb.toString();
    }
}
```
###### \java\seedu\whatnow\commons\core\Messages.java
``` java
package seedu.whatnow.commons.core;

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
###### \java\seedu\whatnow\commons\core\Version.java
``` java
package seedu.whatnow.commons.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a version with major, minor and patch number
 */
public class Version implements Comparable<Version> {

    public static final String VERSION_REGEX = "V(\\d+)\\.(\\d+)\\.(\\d+)(ea)?";

    private static final String EXCEPTION_STRING_NOT_VERSION = "String is not a valid Version. %s";

    private static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);

    private final int major;
    private final int minor;
    private final int patch;
    private final boolean isEarlyAccess;

    public Version(int major, int minor, int patch, boolean isEarlyAccess) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.isEarlyAccess = isEarlyAccess;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    public boolean isEarlyAccess() {
        return isEarlyAccess;
    }

    /**
     * Parses a version number string in the format V1.2.3.
     * @param versionString version number string
     * @return a Version object
     */
    @JsonCreator
    public static Version fromString(String versionString) throws IllegalArgumentException {
        Matcher versionMatcher = VERSION_PATTERN.matcher(versionString);

        if (!versionMatcher.find()) {
            throw new IllegalArgumentException(String.format(EXCEPTION_STRING_NOT_VERSION, versionString));
        }

        return new Version(Integer.parseInt(versionMatcher.group(1)),
                Integer.parseInt(versionMatcher.group(2)),
                Integer.parseInt(versionMatcher.group(3)),
                versionMatcher.group(4) == null ? false : true);
    }

    @JsonValue
    public String toString() {
        return String.format("V%d.%d.%d%s", major, minor, patch, isEarlyAccess ? "ea" : "");
    }

    @Override
    public int compareTo(Version other) {
        return this.major != other.major ? this.major - other.major :
               this.minor != other.minor ? this.minor - other.minor :
               this.patch != other.patch ? this.patch - other.patch :
               this.isEarlyAccess == other.isEarlyAccess() ? 0 :
               this.isEarlyAccess ? -1 : 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        final Version other = (Version) obj;

        return this.compareTo(other) == 0;
    }

    @Override
    public int hashCode() {
        String hash = String.format("%03d%03d%03d", major, minor, patch);
        if (!isEarlyAccess) {
            hash = "1" + hash;
        }
        return Integer.parseInt(hash);
    }
}
```
###### \java\seedu\whatnow\commons\events\BaseEvent.java
``` java
package seedu.whatnow.commons.events;

public abstract class BaseEvent {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     *
     * For example the event manager post method will call any posted event's toString and print it in the console.
     */
    public abstract String toString();

}
```
###### \java\seedu\whatnow\commons\events\model\AddTaskEvent.java
``` java
package seedu.whatnow.commons.events.model;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.task.Task;

/** Indicates that a task has been added to WhatNow*/
public class AddTaskEvent extends BaseEvent {

    public final Task task;

    public AddTaskEvent(Task task){
        this.task = task;
    }

    @Override
    public String toString() {
        return task.getAsText();
    }
}
```
###### \java\seedu\whatnow\commons\events\model\WhatNowChangedEvent.java
``` java
package seedu.whatnow.commons.events.model;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.ReadOnlyWhatNow;

/** Indicates the WhatNow config in the model has changed*/
public class WhatNowChangedEvent extends BaseEvent {

    public final ReadOnlyWhatNow data;

    public WhatNowChangedEvent(ReadOnlyWhatNow data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
```
###### \java\seedu\whatnow\commons\events\ui\IncorrectCommandAttemptedEvent.java
``` java
package seedu.whatnow.commons.events.ui;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\whatnow\commons\events\ui\JumpToListRequestEvent.java
``` java
package seedu.whatnow.commons.events.ui;

import seedu.whatnow.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\whatnow\commons\events\ui\TaskPanelSelectionChangedEvent.java
``` java
package seedu.whatnow.commons.events.ui;

import seedu.whatnow.commons.events.BaseEvent;
import seedu.whatnow.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\whatnow\commons\exceptions\DataConversionException.java
``` java
package seedu.whatnow.commons.exceptions;

/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
```
###### \java\seedu\whatnow\commons\exceptions\DuplicateDataException.java
``` java
package seedu.whatnow.commons.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public abstract class DuplicateDataException extends IllegalValueException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
```
###### \java\seedu\whatnow\commons\util\AppUtil.java
``` java
package seedu.whatnow.commons.util;

import javafx.scene.image.Image;
import seedu.whatnow.MainApp;

/**
 * A container for App specific utility functions
 */
public class AppUtil {

    public static Image getImage(String imagePath) {
        assert imagePath != null;
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

}
```
###### \java\seedu\whatnow\commons\util\CollectionUtil.java
``` java
package seedu.whatnow.commons.util;

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
###### \java\seedu\whatnow\commons\util\StringUtil.java
``` java
package seedu.whatnow.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
}
```
###### \java\seedu\whatnow\commons\util\UrlUtil.java
``` java
package seedu.whatnow.commons.util;

import java.net.URL;

/**
 * A utility class for URL
 */
public class UrlUtil {

    /**
     * Returns true if both URLs have the same base URL
     */
    public static boolean compareBaseUrls(URL url1, URL url2) {

        if (url1 == null || url2 == null) {
            return false;
        }
        return url1.getHost().toLowerCase().replaceFirst("www.", "")
                .equals(url2.getHost().replaceFirst("www.", "").toLowerCase())
                && url1.getPath().replaceAll("/", "").toLowerCase()
                .equals(url2.getPath().replaceAll("/", "").toLowerCase());
    }

}
```
###### \java\seedu\whatnow\commons\util\XmlUtil.java
``` java
package seedu.whatnow.commons.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Helps with reading from and writing to XML files.
 */
public class XmlUtil {

    /**
     * Returns the xml data in the file as an object of the specified type.
     *
     * @param file           Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(File file, Class<T> classToConvert)
            throws FileNotFoundException, JAXBException {

        assert file != null;
        assert classToConvert != null;

        if (!FileUtil.isFileExists(file)) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(file));
    }

    /**
     * Saves the data in the file in xml format.
     *
     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if there is an error during converting the data
     *                               into xml and writing to the file.
     */
    public static <T> void saveDataToFile(File file, T data) throws FileNotFoundException, JAXBException {

        assert file != null;
        assert data != null;

        if (!file.exists()) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(data, file);
    }

}
```
###### \java\seedu\whatnow\logic\commands\ClearCommand.java
``` java
package seedu.whatnow.logic.commands;

import java.util.Stack;

import seedu.whatnow.model.WhatNow;

/**
 * Clears WhatNow.
 */
public class ClearCommand extends UndoAndRedo {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "WhatNow has been cleared!";
    
    public static Stack<WhatNow> reqStack;
    public ClearCommand() {}

```
###### \java\seedu\whatnow\logic\commands\CommandResult.java
``` java
package seedu.whatnow.logic.commands;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    public final String feedbackToUser;

    public CommandResult(String feedbackToUser) {
        assert feedbackToUser != null;
        this.feedbackToUser = feedbackToUser;
    }

}
```
###### \java\seedu\whatnow\logic\commands\IncorrectCommand.java
``` java
package seedu.whatnow.logic.commands;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command {

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() {
        indicateAttemptToExecuteIncorrectCommand();
        return new CommandResult(feedbackToUser);
    }

}

```
###### \java\seedu\whatnow\MainApp.java
``` java
import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.EventsCenter;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.core.Version;
import seedu.whatnow.commons.events.ui.ExitAppRequestEvent;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.ConfigUtil;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.Logic;
import seedu.whatnow.logic.LogicManager;
import seedu.whatnow.model.*;
import seedu.whatnow.storage.Storage;
import seedu.whatnow.storage.StorageManager;
import seedu.whatnow.ui.Ui;
import seedu.whatnow.ui.UiManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */

public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing WhatNow ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getWhatNowFilePath(), config.getUserPrefsFilePath());
        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }
    
    public void setConfig(Config config) {
        this.config = config;
    }
    
    public Config getConfig() {
        return this.config;
    }
    
    public void setStorage(Storage storage){
        this.storage = storage;
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyWhatNow> whatNowOptional;
        ReadOnlyWhatNow initialData;
        try {
            whatNowOptional = storage.readWhatNow();
            if(!whatNowOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty WhatNow");
            }
            initialData = whatNowOptional.orElse(new WhatNow());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty WhatNow");
            initialData = new WhatNow();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty WhatNow");
            initialData = new WhatNow();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if(configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty WhatNow");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting WhatNow " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping WhatNow ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    private void indicateWhatNowChanged() {
        raise(new WhatNowChangedEvent(whatNow));
    }
```
###### \java\seedu\whatnow\model\tag\Tag.java
``` java
package seedu.whatnow.model.tag;

import seedu.whatnow.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in WhatNow.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}*";

    public String tagName;

    public Tag() {
    }

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidTagName(name)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = name;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
```
###### \java\seedu\whatnow\model\tag\UniqueTagList.java
``` java
package seedu.whatnow.model.tag;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatnow.commons.exceptions.DuplicateDataException;
import seedu.whatnow.commons.util.CollectionUtil;

import java.util.*;

/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Tag#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTagList implements Iterable<Tag> {

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException() {
            super("Operation would result in duplicate tags");
        }
    }

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTagList() {}

    /**
     * Varargs/array constructor, enforces no nulls or duplicates.
     */
    public UniqueTagList(Tag... tags) throws DuplicateTagException {
        assert !CollectionUtil.isAnyNull((Object[]) tags);
        final List<Tag> initialTags = Arrays.asList(tags);
        if (!CollectionUtil.elementsAreUnique(initialTags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(initialTags);
    }

    /**
     * java collections constructor, enforces no null or duplicate elements.
     */
    public UniqueTagList(Collection<Tag> tags) throws DuplicateTagException {
        CollectionUtil.assertNoNullElements(tags);
        if (!CollectionUtil.elementsAreUnique(tags)) {
            throw new DuplicateTagException();
        }
        internalList.addAll(tags);
    }

    /**
     * java set constructor, enforces no nulls.
     */
    public UniqueTagList(Set<Tag> tags) {
        CollectionUtil.assertNoNullElements(tags);
        internalList.addAll(tags);
    }

    /**
     * Copy constructor, insulates from changes in source.
     */
    public UniqueTagList(UniqueTagList source) {
        internalList.addAll(source.internalList); // insulate internal list from changes in argument
    }

    /**
     * All tags in this list as a Set. This set is mutable and change-insulated against the internal list.
     */
    public Set<Tag> toSet() {
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        this.internalList.clear();
        this.internalList.addAll(replacement.internalList);
    }

    /**
     * Adds every tag from the argument list that does not yet exist in this list.
     */
    public void mergeFrom(UniqueTagList tags) {
        final Set<Tag> alreadyInside = this.toSet();
        for (Tag tag : tags) {
            if (!alreadyInside.contains(tag)) {
                internalList.add(tag);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent Tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }
    
    /**
     * Returns the number of tags in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Adds a Tag to the list.
     *
     * @throws DuplicateTagException if the Tag to add is a duplicate of an existing Tag in the list.
     */
    public void add(Tag toAdd) throws DuplicateTagException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    public ObservableList<Tag> getInternalList() {
        return internalList;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UniqueTagList) {
            UniqueTagList tagList = (UniqueTagList) other;
            if (this.internalList.size() == tagList.size()) {
                for (Tag tag : this.internalList) {
                    if (!(((UniqueTagList) other).getInternalList()).contains(tag)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
 
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\whatnow\model\UserPrefs.java
``` java
import java.util.Objects;

import seedu.whatnow.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    public GuiSettings guiSettings;

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public UserPrefs(){
        this.setGuiSettings(500, 500, 0, 0);
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof UserPrefs)){ //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs)other;

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString(){
        return guiSettings.toString();
    }

}
```
###### \java\seedu\whatnow\model\WhatNow.java
``` java
import javafx.collections.ObservableList;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the WhatNow level
 * Duplicates are not allowed (by .equals comparison)
 */
public class WhatNow implements ReadOnlyWhatNow {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;
    private UniqueTaskList backUpTasks;
    private UniqueTagList backUpTags;
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public WhatNow() {}

    /**
     * Tasks and Tags are copied into this whatnow
     */
    public WhatNow(ReadOnlyWhatNow toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this whatnow
     */
    public WhatNow(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyWhatNow getEmptyWhatNow() {
        return new WhatNow();
    }
   

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        
    	setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyWhatNow newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }
	public void revertEmptyWhatNow(ReadOnlyWhatNow backUp) {
		resetData(backUp.getTaskList(),backUp.getTagList());
	}

//// task-level operations

    /**
     * Adds a task to WhatNow.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(p);
        tasks.add(p);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    /**
     * Remove a task from WhatNow.
     *
     * @throws UniqueTaskList.TaskNotFoundException if the task does not exist.
     */
    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean changeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        } 
    }
    /**
     * Updates a task on WhatNow.
     * 
     * @throws UniqueTaskList.TaskNotFoundException
     */
    public boolean updateTask(ReadOnlyTask old, Task toUpdate) throws TaskNotFoundException, DuplicateTaskException {
    	if (tasks.update(old, toUpdate)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    /**
     * Marks a task on WhatNow as completed.
     * 
     * @throws UniqueTaskList.TaskNotFoundException
     */
    public boolean markTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.mark(target)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

	public boolean unMarkTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException {
		if(tasks.unmark(target)) {
			return true;
		} else {
			throw new UniqueTaskList.TaskNotFoundException();
		}
	}


//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WhatNow // instanceof handles nulls
                && this.tasks.equals(((WhatNow) other).tasks)
                && this.tags.equals(((WhatNow) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
```
###### \java\seedu\whatnow\storage\XmlAdaptedTag.java
``` java
import javax.xml.bind.annotation.XmlValue;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedTag {

    @XmlValue
    public String tagName;

    /**
     * No-arg constructor for JAXB use.
     */
    public XmlAdaptedTag() {}

    /**
     * Converts a given Tag into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedTag(Tag source) {
        tagName = source.tagName;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public Tag toModelType() throws IllegalValueException {
        return new Tag(tagName);
    }

}
```
###### \java\seedu\whatnow\ui\BrowserPanel.java
``` java
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.util.FxViewUtil;
import seedu.whatnow.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart{

    private static Logger logger = LogsCenter.getLogger(BrowserPanel.class);
    private WebView browser;

    /**
     * Constructor is kept private as {@link #load(AnchorPane)} is the only way to create a BrowserPanel.
     */
    private BrowserPanel() {

    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

    /**
     * Factory method for creating a Browser Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public static BrowserPanel load(AnchorPane placeholder){
        logger.info("Initializing browser");
        BrowserPanel browserPanel = new BrowserPanel();
        browserPanel.browser = new WebView();
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browserPanel.browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browserPanel.browser);
        return browserPanel;
    }

    public void loadTaskPage(ReadOnlyTask task) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + task.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url){
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
```
###### \java\seedu\whatnow\ui\HelpWindow.java
``` java
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.util.FxViewUtil;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    private static final String FXML = "HelpWindow.fxml";
    private static final String TITLE = "Help";
    private static final String USERGUIDE_URL = "https://github.com/CS2103AUG2016-W15-C4/main/blob/master/docs/HelpPage.md#command-summary";

    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);

        WebView browser = new WebView();
        browser.getEngine().load(USERGUIDE_URL);
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
```
###### \java\seedu\whatnow\ui\StatusBarFooter.java
``` java
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.events.model.ConfigChangedEvent;
import seedu.whatnow.commons.events.model.WhatNowChangedEvent;
import seedu.whatnow.commons.util.FxViewUtil;

import org.controlsfx.control.StatusBar;

import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Logger;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart {
    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);
    private StatusBar syncStatus;
    private StatusBar saveLocationStatus;

    private GridPane mainPane;

    @FXML
    private AnchorPane saveLocStatusBarPane;

    @FXML
    private AnchorPane syncStatusBarPane;

    private AnchorPane placeHolder;

    private static final String FXML = "StatusBarFooter.fxml";

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder, String saveLocation) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage, placeHolder, new StatusBarFooter());
        statusBarFooter.configure(saveLocation);
        return statusBarFooter;
    }

    public void configure(String saveLocation) {
        addMainPane();
        addSyncStatus();
        setSyncStatus("Not updated yet in this session");
        addSaveLocation();
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(mainPane);
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void addSaveLocation() {
        this.saveLocationStatus = new StatusBar();
        FxViewUtil.applyAnchorBoundaryParameters(saveLocationStatus, 0.0, 0.0, 0.0, 0.0);
        saveLocStatusBarPane.getChildren().add(saveLocationStatus);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }
    
    private void setSaveLocationStatus(Path destination) {
        this.saveLocationStatus.setText(destination.toString());
    }

    private void addSyncStatus() {
        this.syncStatus = new StatusBar();
        FxViewUtil.applyAnchorBoundaryParameters(syncStatus, 0.0, 0.0, 0.0, 0.0);
        syncStatusBarPane.getChildren().add(syncStatus);
    }

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    public void handleWhatNowChangedEvent(WhatNowChangedEvent abce) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus("Last Updated: " + lastUpdated);
    }
    
    @Subscribe
    public void handleFileLocationChangedEvent(ConfigChangedEvent event) {
        setSaveLocationStatus(event.destination);
    }
    
}
```
###### \java\seedu\whatnow\ui\Ui.java
``` java
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}
```
