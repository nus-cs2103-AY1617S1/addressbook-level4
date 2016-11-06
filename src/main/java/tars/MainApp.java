package tars;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import tars.model.*;
import tars.commons.core.Config;
import tars.commons.core.EventsCenter;
import tars.commons.core.LogsCenter;
import tars.commons.core.Version;
import tars.commons.events.ui.ExitAppRequestEvent;
import tars.commons.exceptions.DataConversionException;
import tars.commons.util.ConfigUtil;
import tars.commons.util.DateTimeUtil;
import tars.commons.util.StringUtil;
import tars.logic.*;
import tars.storage.Storage;
import tars.storage.StorageManager;
import tars.ui.Ui;
import tars.ui.UiManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final String INITIALIZING_HEADER =
            "=============================[ Initializing Tars ]===========================";
    private static final String PARAMETER_CONFIG = "config";
    private static final String LOG_MESSAGE_PREFS_SAVE_FAILURE =
            "Failed to save preferences ";
    private static final String DAY_STRING_TODAY = "today";
    private static String LOG_MESSAGE_PREFS_FILE_INCORRECT_FORMAT =
            "UserPrefs file at %s is not in the correct format. Using default user prefs";
    private static String LOG_MESSAGE_CONFIG_FILE_INCORRECT_FORMAT =
            "Config file at %s is not in the correct format. Using default config properties";
    private static final String LOG_MESSAGE_DATA_FILE_NOT_FOUND =
            "Data file not found. Will be starting with an empty Tars";
    private static final String LOG_MESSAGE_DATA_FILE_INCORRECT_FORMAT =
            "Data file not in the correct format. Will be starting with an empty Tars";
    private static final String LOG_MESSAGE_DATA_FILE_CORRUPTED =
            "Problem while reading from the file. Will be starting with an empty Tars";
    private static final String LOG_MESSAGE_CUSTOM_CONFIG_FILE =
            "Custom Config file specified ";
    private static final String LOG_MESSAGE_USING_CONFIG_FILE =
            "Using config file : ";
    private static final String LOG_MESSAGE_CONFIG_FILE_SAVE_FAILURE =
            "Failed to save config file : ";
    private static final String LOG_MESSAGE_USING_PREFS_FILE =
            "Using prefs file : ";
    private static final String LOG_MESSAGE_PROBLEM_READING_FROM_FILE =
            "Problem while reading from the file. . Will be starting with an empty Tars";
    private static final String LOG_MESSAGE_STARTING_TARS = "Starting Tars ";
    private static final String STOPPING_TARS_HEADER =
            "============================ [ Stopping TARS ] =============================";
    private static final int SYSTEM_EXIT_NO_ERROR = 0;


    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(1, 0, 0, true);



    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    @Override
    public void init() throws Exception {
        logger.info(INITIALIZING_HEADER);
        super.init();

        config = initConfig(getApplicationParameter(PARAMETER_CONFIG));
        storage = new StorageManager(config.getTarsFilePath(),
                config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        // to improve the natty parser runtime for the first query
        new Thread(() -> DateTimeUtil.parseStringToDateTime(DAY_STRING_TODAY))
                .start();

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage) {
        Optional<ReadOnlyTars> tarsOptional;
        ReadOnlyTars initialData;
        try {
            tarsOptional = storage.readTars();
            if (!tarsOptional.isPresent()) {
                logger.info(LOG_MESSAGE_DATA_FILE_NOT_FOUND);
            }
            initialData = tarsOptional.orElse(new Tars());
        } catch (DataConversionException e) {
            logger.warning(LOG_MESSAGE_DATA_FILE_INCORRECT_FORMAT);
            initialData = new Tars();
        } catch (FileNotFoundException e) {
            logger.warning(LOG_MESSAGE_DATA_FILE_CORRUPTED);
            initialData = new Tars();
        }

        return new ModelManager(initialData);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info(LOG_MESSAGE_CUSTOM_CONFIG_FILE + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info(LOG_MESSAGE_USING_CONFIG_FILE + configFilePathUsed);

        try {
            Optional<Config> configOptional =
                    ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning(String.format(
                    LOG_MESSAGE_CONFIG_FILE_INCORRECT_FORMAT, configFilePath));
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning(LOG_MESSAGE_CONFIG_FILE_SAVE_FAILURE
                    + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(Config config) {
        assert config != null;

        String prefsFilePath = config.getUserPrefsFilePath();
        logger.info(LOG_MESSAGE_USING_PREFS_FILE + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning(String.format(
                    LOG_MESSAGE_PREFS_FILE_INCORRECT_FORMAT, prefsFilePath));
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning(LOG_MESSAGE_PROBLEM_READING_FROM_FILE);
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning(LOG_MESSAGE_CONFIG_FILE_SAVE_FAILURE
                    + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info(LOG_MESSAGE_STARTING_TARS + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info(STOPPING_TARS_HEADER);
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe(
                    LOG_MESSAGE_PREFS_SAVE_FAILURE + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(SYSTEM_EXIT_NO_ERROR);
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
