package seedu.oneline;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.oneline.commons.core.Config;
import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.LogsCenter;
import seedu.oneline.commons.core.Version;
import seedu.oneline.commons.events.storage.DataSavingExceptionEvent;
import seedu.oneline.commons.events.storage.StorageLocationChangedEvent;
import seedu.oneline.commons.events.ui.ExitAppRequestEvent;
import seedu.oneline.commons.exceptions.DataConversionException;
import seedu.oneline.commons.util.ConfigUtil;
import seedu.oneline.commons.util.StringUtil;
import seedu.oneline.logic.Logic;
import seedu.oneline.logic.LogicManager;
import seedu.oneline.model.*;
import seedu.oneline.storage.Storage;
import seedu.oneline.storage.StorageManager;
import seedu.oneline.ui.Ui;
import seedu.oneline.ui.UiManager;

import java.io.FileNotFoundException;
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
        logger.info("=============================[ Initializing OneLine ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskBookFilePath(), config.getUserPrefsFilePath());

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

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskBook> addressBookOptional;
        ReadOnlyTaskBook initialData;
        try {
            addressBookOptional = storage.readTaskBook();
            if(!addressBookOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty Task Book");
            }
            initialData = addressBookOptional.orElse(new TaskBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Task Book");
            initialData = new TaskBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty Task Book");
            initialData = new TaskBook();
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
            logger.warning("Problem while reading from the file. . Will be starting with an empty Task Book");
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
        logger.info("============================ [ Stopping OneLine ] =============================");
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
    
    @Subscribe
    private void handleStorageChangedEvent(StorageLocationChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        
        // Set the new storage location in the config object
        config.setStorageLocation(event.getStoragePath());
        
        try {
            // Save the config object so that the changed file location is updated on next app reload
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            ReadOnlyTaskBook tasks = storage.readTaskBook().orElse(new TaskBook());
            
            // Reinitialize the current storage object
            storage = new StorageManager(config.getTaskBookFilePath(), config.getUserPrefsFilePath());
            
            // Save the current status of taskBook into the new location
            // This is if we close the app without adding new tasks
            storage.saveTaskBook(tasks);
        
        } catch (IOException iox) {
            EventsCenter.getInstance().post(new DataSavingExceptionEvent(iox));
        } catch (DataConversionException dcex) {
            EventsCenter.getInstance().post(new DataSavingExceptionEvent(dcex));
        }
    }   
}
