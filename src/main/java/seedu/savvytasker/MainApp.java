package seedu.savvytasker;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.savvytasker.commons.core.Config;
import seedu.savvytasker.commons.core.EventsCenter;
import seedu.savvytasker.commons.core.LogsCenter;
import seedu.savvytasker.commons.core.Version;
import seedu.savvytasker.commons.events.ui.ExitAppRequestEvent;
import seedu.savvytasker.commons.exceptions.DataConversionException;
import seedu.savvytasker.commons.util.ConfigUtil;
import seedu.savvytasker.commons.util.StringUtil;
import seedu.savvytasker.logic.Logic;
import seedu.savvytasker.logic.LogicManager;
import seedu.savvytasker.model.Model;
import seedu.savvytasker.model.ModelManager;
import seedu.savvytasker.model.ReadOnlySavvyTasker;
import seedu.savvytasker.model.SavvyTasker;
import seedu.savvytasker.model.UserPrefs;
import seedu.savvytasker.storage.Storage;
import seedu.savvytasker.storage.StorageManager;
import seedu.savvytasker.ui.Ui;
import seedu.savvytasker.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(0, 4, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Savvy Tasker ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        storage = new StorageManager(config.getAddressBookFilePath(), config.getUserPrefsFilePath());

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
        Optional<ReadOnlySavvyTasker> savvyTaskerOptional;
        ReadOnlySavvyTasker initialData;
        try {
            savvyTaskerOptional = storage.readSavvyTasker();
            if(!savvyTaskerOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty Savvy Tasker");
            }
            initialData = savvyTaskerOptional.orElse(new SavvyTasker());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Savvy Tasker");
            initialData = new SavvyTasker();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. . Will be starting with an empty Savvy Tasker");
            initialData = new SavvyTasker();
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
            logger.warning("Problem while reading from the file. . Will be starting with an empty Savvy Tasker");
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
        logger.info("Starting Savvy Tasker " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Savvy Tasker ] =============================");
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
