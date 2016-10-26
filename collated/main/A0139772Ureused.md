# A0139772Ureused
###### \src\main\java\seedu\whatnow\commons\core\ComponentManager.java
``` java
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
###### \src\main\java\seedu\whatnow\commons\core\GuiSettings.java
``` java
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
###### \src\main\java\seedu\whatnow\commons\events\BaseEvent.java
``` java
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
###### \src\main\java\seedu\whatnow\commons\exceptions\DataConversionException.java
``` java
/**
 * Represents an error during conversion of data from one format to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
```
###### \src\main\java\seedu\whatnow\commons\util\FileUtil.java
``` java
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
###### \src\main\java\seedu\whatnow\logic\commands\CommandResult.java
``` java
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
###### \src\main\java\seedu\whatnow\logic\commands\DeleteCommand.java
``` java
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from WhatNow.
 */
public class DeleteCommand extends UndoAndRedo {

	public static final String COMMAND_WORD = "delete";

	public static final String MESSAGE_USAGE = COMMAND_WORD
			+ ": Deletes the task identified by the index number used in the last task listing.\n"
			+ "Parameters: INDEX (must be a positive integer)\n"
			+ "Example: " + COMMAND_WORD + " 1";

	public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

	private static final String TASK_TYPE_FLOATING = "todo";

	private final int targetIndex;
	private final String taskType;

	public DeleteCommand(String taskType, int targetIndex) {
		this.targetIndex = targetIndex;
		this.taskType = taskType;
	}


	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList;

		if (taskType.equals(TASK_TYPE_FLOATING)) {
			lastShownList = model.getCurrentFilteredTaskList();
		} else {
			lastShownList = model.getCurrentFilteredScheduleList();
		}
		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}
		ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

		assert model != null;	 
		try {
			model.deleteTask(taskToDelete);
			model.getUndoStack().push(this);
			model.getDeletedStackOfTasks().push(taskToDelete);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}
		return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
	}

```
###### \src\main\java\seedu\whatnow\logic\LogicManager.java
``` java
import javafx.collections.ObservableList;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.logic.commands.Command;
import seedu.whatnow.logic.commands.CommandResult;
import seedu.whatnow.logic.parser.Parser;
import seedu.whatnow.model.Model;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.whatnow.storage.Storage;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException, DuplicateTaskException, TaskNotFoundException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
    
    @Override
    public ObservableList<ReadOnlyTask> getFilteredScheduleList() {
        return model.getFilteredScheduleList();
    }
}
```
###### \src\main\java\seedu\whatnow\MainApp.java
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
###### \src\main\java\seedu\whatnow\model\ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    private void indicateWhatNowChanged() {
        raise(new WhatNowChangedEvent(whatNow));
    }
```
###### \src\main\java\seedu\whatnow\model\tag\Tag.java
``` java

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
###### \src\main\java\seedu\whatnow\model\task\Name.java
``` java
import seedu.whatnow.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in WhatNow.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should not contain quotation marks \"";
    public static final String NAME_VALIDATION_REGEX = "[^\"]+";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        name = name.trim();
        if (!isValidName(name)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = name;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && this.fullName.equals(((Name) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \src\main\java\seedu\whatnow\model\UserPrefs.java
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
###### \src\main\java\seedu\whatnow\model\WhatNow.java
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
###### \src\main\java\seedu\whatnow\storage\XmlAdaptedTag.java
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
###### \src\main\java\seedu\whatnow\ui\BrowserPanel.java
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
###### \src\main\java\seedu\whatnow\ui\HelpWindow.java
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
###### \src\main\java\seedu\whatnow\ui\StatusBarFooter.java
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
###### \src\main\java\seedu\whatnow\ui\Ui.java
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
