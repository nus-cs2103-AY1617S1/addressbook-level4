package seedu.whatnow.ui;
//@@author A0139772U
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.whatnow.MainApp;
import seedu.whatnow.commons.core.ComponentManager;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.events.model.AddTaskEvent;
import seedu.whatnow.commons.events.model.UpdateTaskEvent;
import seedu.whatnow.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatnow.commons.events.ui.JumpToListRequestEvent;
import seedu.whatnow.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatnow.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.logic.Logic;
import seedu.whatnow.model.UserPrefs;
import seedu.whatnow.model.task.Task;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/whatnow_32.png";
    private static final String MESSAGE_TASK_ADDED = "A task was added";
    private static final String MESSAGE_TASK_UPDATED = "A task was updated";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }
    
    @Subscribe
    public void handleAddTaskEvent(AddTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, MESSAGE_TASK_ADDED));
        Task task = event.task;
        
        if (task.getTaskType().equals("floating")) {
            mainWindow.getTaskListPanel().scrollTo(logic.getFilteredTaskList().indexOf(task));
            mainWindow.getScheduleListPanel().clear();
        } else {
            mainWindow.getScheduleListPanel().scrollTo(logic.getFilteredScheduleList().indexOf(task));
            mainWindow.getTaskListPanel().clear();
        }
    }
    
    @Subscribe
    public void handleUpdateTaskEvent(UpdateTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, MESSAGE_TASK_UPDATED));
        Task task = event.task;
        
        if (task.getTaskType().equals("floating")) {
            mainWindow.getTaskListPanel().scrollTo(logic.getFilteredTaskList().indexOf(task));
            mainWindow.getScheduleListPanel().clear();
        } else {
            mainWindow.getScheduleListPanel().scrollTo(logic.getFilteredScheduleList().indexOf(task));
            mainWindow.getTaskListPanel().clear();
        }
    }
}
