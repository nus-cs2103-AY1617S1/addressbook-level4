package seedu.address;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.*;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing " + Config.ApplicationTitle + " ]===========================");
        super.init();

        storage = new StorageManager(Config.DefaultToDoListFilePath, Config.DefaultUserPrefsFilePath);

        userPrefs = initPrefs();

        initLogging();

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, userPrefs);

        initEventsCenter();
    }

    private String getApplicationParameter(String parameterName){
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyToDoList> toDoListOptional;
        ReadOnlyToDoList initialToDoList;
        try {
            toDoListOptional = storage.readToDoList();
            if(!toDoListOptional.isPresent()){
                logger.info("Data file not found. Will be starting with an empty to-do list");
            }
            initialToDoList = toDoListOptional.orElse(new ToDoList());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty to-do list");
            initialToDoList = new ToDoList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty to-do list");
            initialToDoList = new ToDoList();
        }

        return new ModelManager(initialToDoList, userPrefs);
    }

    private void initLogging() {
        LogsCenter.init(Config.LogLevel);
    }

    protected UserPrefs initPrefs() {
        String prefsFilePath = Config.DefaultUserPrefsFilePath;
        logger.info("Using prefs file: " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. " +
                    "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file: " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting " + Config.ApplicationTitle + " " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping " + Config.ApplicationTitle + " ] =============================");
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
