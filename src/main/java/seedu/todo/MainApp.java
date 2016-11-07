package seedu.todo;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.Version;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.Dispatcher;
import seedu.todo.logic.Logic;
import seedu.todo.logic.TodoDispatcher;
import seedu.todo.logic.TodoLogic;
import seedu.todo.logic.parser.Parser;
import seedu.todo.logic.parser.TodoParser;
import seedu.todo.model.Model;
import seedu.todo.model.TodoModel;
import seedu.todo.model.UserPrefs;
import seedu.todo.ui.Ui;
import seedu.todo.ui.UiManager;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static final Version VERSION = new Version(0, 5, 0, true);

    protected Ui ui;
    protected Logic logic;
    protected Model model;
    protected Dispatcher dispatcher; 
    protected Parser parser;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Uncle Jim's Discount To-do List ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));
        userPrefs = new UserPrefs(config);

        initLogging(config);

        parser = new TodoParser();

        model = new TodoModel(config);
        
        dispatcher = new TodoDispatcher();
        logic = new TodoLogic(parser, model, dispatcher);

        ui = new UiManager(logic, config, userPrefs, model);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
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

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Uncle Jim's Discount To-do List" + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Uncle Jim's Discount To-do List ] =============================");
        ui.stop();

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
