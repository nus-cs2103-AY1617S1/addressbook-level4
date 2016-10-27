package seedu.flexitrack;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.flexitrack.commons.core.Config;
import seedu.flexitrack.commons.core.EventsCenter;
import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.commons.core.Version;
import seedu.flexitrack.commons.events.ui.ExitAppRequestEvent;
import seedu.flexitrack.commons.exceptions.DataConversionException;
import seedu.flexitrack.commons.util.ConfigUtil;
import seedu.flexitrack.commons.util.StringUtil;
import seedu.flexitrack.logic.Logic;
import seedu.flexitrack.logic.LogicManager;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.ModelManager;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.UserPrefs;
import seedu.flexitrack.storage.Storage;
import seedu.flexitrack.storage.StorageManager;
import seedu.flexitrack.ui.Ui;
import seedu.flexitrack.ui.UiManager;

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

    public MainApp() {
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing FlexiTrack ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getFlexiTrackFilePath(), config.getUserPrefsFilePath());

        userPrefs = initPrefs(config);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyFlexiTrack> flexiTrackOptional;
        ReadOnlyFlexiTrack initialData;
        try {
            flexiTrackOptional = storage.readFlexiTrack();
            if (!flexiTrackOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty FlexiTrack");
            }
            initialData = flexiTrackOptional.orElse(new FlexiTrack());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FlexiTrack");
            initialData = new FlexiTrack();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty FlexiTrack");
            initialData = new FlexiTrack();
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

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        // Update config file in case it was missing to begin with or there are
        // new/unused fields
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
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty FlexiTrack");
            initializedPrefs = new UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are
        // new/unused fields
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
        logger.info("Starting FlexiTrack " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping FlexiTrack ] =============================");
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
