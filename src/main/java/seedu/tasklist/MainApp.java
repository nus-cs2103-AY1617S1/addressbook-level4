package seedu.tasklist;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.tasklist.commons.core.Config;
import seedu.tasklist.commons.core.EventsCenter;
import seedu.tasklist.commons.core.LogsCenter;
import seedu.tasklist.commons.core.Version;
import seedu.tasklist.commons.events.TickEvent;
import seedu.tasklist.commons.events.ui.ExitAppRequestEvent;
import seedu.tasklist.commons.exceptions.DataConversionException;
import seedu.tasklist.commons.util.ConfigUtil;
import seedu.tasklist.commons.util.StringUtil;
import seedu.tasklist.logic.Logic;
import seedu.tasklist.logic.LogicManager;
import seedu.tasklist.model.*;
import seedu.tasklist.storage.Storage;
import seedu.tasklist.storage.StorageManager;
import seedu.tasklist.ui.Ui;
import seedu.tasklist.ui.UiManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

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
    protected Thread updateThread;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Smart Scheduler ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getTaskListFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);
        
        initThread();

        initEventsCenter();
    }
    
  //@@author A0146107M
    private void initThread(){
    	updateThread = new Thread(){
    		public void run(){
				long timeToNextMinute = 60 - Calendar.getInstance().get(Calendar.SECOND);
				try {
					Thread.sleep(timeToNextMinute*1000);
    				while (true) {
						Platform.runLater(() -> {
							EventsCenter.getInstance().post(new TickEvent());
						});
						Thread.sleep(60 * 1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
    		}
    	};
    	updateThread.start();
    }
  //@@author

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskList> taskListOptional;
        ReadOnlyTaskList initialData;
        try {
            taskListOptional = storage.readTaskList();
            if(!taskListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty Task List");
            }
            initialData = taskListOptional.orElse(new TaskList());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Task List");
            initialData = new TaskList();
        } catch (FileNotFoundException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty Task List");
            initialData = new TaskList();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) throws JSONException, IOException, ParseException {
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
            logger.warning("Problem while reading from the file. . Will be starting with an empty Task List");
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
        logger.info("Starting Smart Scheduler " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Smart Scheduler ] =============================");
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
