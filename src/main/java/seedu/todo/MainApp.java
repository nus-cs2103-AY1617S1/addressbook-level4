package seedu.todo;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.todo.commons.core.EventsCenter;
import seedu.todo.ui.UiManager;
import seedu.todo.ui.views.IndexView;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.core.Version;
import seedu.todo.commons.events.ui.ExitAppRequestEvent;

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

    protected UiManager ui;
    protected Config config;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        super.init();
        
        // Initialize config from config file, or create a new one.
        config = initConfig(getApplicationParameter("config"));
        
        // Initialize logging
        initLogging(config);
        
        // Initialize events center
        initEventsCenter();
        
        // Initialize UI
        ui = new UiManager(config);
    }

    @Override
    public void start(Stage primaryStage) {
    	ui.start(primaryStage);
    	IndexView index = new IndexView();
    	index.passInProps(view -> {
    		IndexView modifyView = (IndexView) view;
    		modifyView.indexTextValue = "Dynamic text passed to IndexView";
    		return modifyView;
    	});
    	ui.loadView(index);
    }

    @Override
    public void stop() {
        ui.stop();
        Platform.exit();
        System.exit(0);
    }
    
    /** ================== UTILS ====================== **/

    /**
     * Gets command-line parameter by name.
     * 
     * @param parameterName   Name of parameter
     * @return   Value of parameter
     */
    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }
    
    /** ================== INITIALIZATION ====================== **/

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    private Config initConfig(String configFilePath) {
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

        // Update config file in case it was missing to begin with or there are new/unused fields
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
    
    /** ================== SUBSCRIPTIONS ====================== **/

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }
    
    /** ================== MAIN METHOD ====================== **/

    public static void main(String[] args) {
        launch(args);
    }
}
