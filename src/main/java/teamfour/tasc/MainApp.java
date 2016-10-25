package teamfour.tasc;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import teamfour.tasc.commons.core.Config;
import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.core.Version;
import teamfour.tasc.commons.events.storage.FileRelocateEvent;
import teamfour.tasc.commons.events.storage.RequestTaskListRenameEvent;
import teamfour.tasc.commons.events.storage.RequestTaskListSwitchEvent;
import teamfour.tasc.commons.events.ui.ExitAppRequestEvent;
import teamfour.tasc.commons.exceptions.DataConversionException;
import teamfour.tasc.commons.exceptions.TaskListFileExistException;
import teamfour.tasc.commons.util.ConfigUtil;
import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.logic.Logic;
import teamfour.tasc.logic.LogicManager;
import teamfour.tasc.model.Model;
import teamfour.tasc.model.ModelManager;
import teamfour.tasc.model.ReadOnlyTaskList;
import teamfour.tasc.model.TaskList;
import teamfour.tasc.model.UserPrefs;
import teamfour.tasc.storage.Storage;
import teamfour.tasc.storage.StorageManager;
import teamfour.tasc.ui.Ui;
import teamfour.tasc.ui.UiManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Model model;
    protected UserPrefs userPrefs;
    protected Config config;
    protected Storage storage;
    private static String newTaskListFilePath;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        newTaskListFilePath = config.getTaskListFilePath();
        storage = new StorageManager(config.getTaskListFilePathAndName(), config.getUserPrefsFilePath());
        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private static Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskList> taskListOptional;
        ReadOnlyTaskList initialData;
        try {
            taskListOptional = storage.readTaskList();
            if(!taskListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty TaskList");
            }
            initialData = taskListOptional.orElse(new TaskList());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskList");
            initialData = new TaskList();
        } catch (FileNotFoundException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty TaskList");
            initialData = new TaskList();
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
            logger.warning("Problem while reading from the file. . Will be starting with an empty AddressBook");
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
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
    
    public void setDataStorageFilePath(String newPath) throws IOException, JAXBException, DataConversionException {
        newTaskListFilePath = newPath;
        config.changeTaskListFilePath(newTaskListFilePath);
        storage.changeTaskListStorage(config.getTaskListFilePathAndName());
    }
    
    public static String getDataStorageFilePath() {
        return newTaskListFilePath;
    }
    
    @Subscribe
    public void handleFileRelocateEvent(FileRelocateEvent event) 
            throws IOException, JAXBException, DataConversionException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setDataStorageFilePath(event.getDestination());
    }
    
    @Subscribe
    public void handleRequestTaskListSwitchEvent(RequestTaskListSwitchEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        config.switchToNewTaskList(event.getFilename());
        this.stop();
    }
    
    @Subscribe
    public void handleRequestTaskListRenameEvent(RequestTaskListRenameEvent event) throws IOException, TaskListFileExistException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        config.renameCurrentTaskList(event.getNewFilename());
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
