package seedu.cmdo;

import com.google.common.eventbus.Subscribe;//don't need
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.cmdo.commons.core.Config;
import seedu.cmdo.commons.core.EventsCenter;
import seedu.cmdo.commons.core.LogsCenter;
import seedu.cmdo.commons.core.Version;
import seedu.cmdo.commons.events.ui.ExitAppRequestEvent;
import seedu.cmdo.commons.events.ui.StorageFileChangedEvent;
import seedu.cmdo.commons.exceptions.DataConversionException;
import seedu.cmdo.commons.util.ConfigUtil;
import seedu.cmdo.commons.util.StringUtil;
import seedu.cmdo.logic.Logic;
import seedu.cmdo.logic.LogicManager;
import seedu.cmdo.model.*;
import seedu.cmdo.storage.Storage;
import seedu.cmdo.storage.StorageManager;
import seedu.cmdo.ui.Ui;
import seedu.cmdo.ui.UiManager;

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

    public static final Version VERSION = new Version(0, 0, 3, true);

    protected Ui ui  ;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}


	@Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ToDoList ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
                
        storage = new StorageManager(config.getToDoListFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);
                
        model = initModelManager(storage, userPrefs);
        
        logger.info("Init model success");
        
        syncUserPrefsToConfig();
        
        initLogging(config);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);
        
        initEventsCenter();
    }
	
    /** 
     * Read user defined settings, if any 
     * @throws Exception
     * 
     * @@author A0139661Y
     */
	private void syncUserPrefsToConfig() throws Exception {
        config.setToDoListFilePath(userPrefs.getStorageSettings().getFilePath());
        storage.updateFilePathInUserPrefs(config.getToDoListFilePath());
        storage.saveToDoList(model.getToDoList());
	}

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyToDoList> toDoListOptional;
        ReadOnlyToDoList initialData;
        try {
            toDoListOptional = storage.readToDoList();
            if(!toDoListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty ToDoList");
            }
            initialData = toDoListOptional.orElse(new ToDoList());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ToDoList");
            initialData = new ToDoList();
        } catch (FileNotFoundException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty ToDoList");
            initialData = new ToDoList();
        } catch(Exception e) {
        	logger.warning("Data file not found. Will be starting with an empty ToDoList");
        	initialData = new ToDoList();
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
            logger.warning("Problem while reading from the file. . Will be starting with an empty ToDoList");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
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
        logger.info("Starting ToDoList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping CMDo ] =============================");
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
    
    /**
     * Handles an event where the storage file has been changed.
     * @param event
     * 
     * @@author A0139661Y
     */
    @Subscribe
    public void handleStorageFileChangedEvent(StorageFileChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        try {
        	syncUserPrefsToConfig();
        } catch (Exception e) {
        	logger.severe("Failed to update config file.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
