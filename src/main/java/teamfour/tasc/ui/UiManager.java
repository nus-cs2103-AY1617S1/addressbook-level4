package teamfour.tasc.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import teamfour.tasc.MainApp;
import teamfour.tasc.commons.core.ComponentManager;
import teamfour.tasc.commons.core.Config;
import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.events.storage.DataSavingExceptionEvent;
import teamfour.tasc.commons.events.ui.ChangeCalendarViewRequestEvent;
import teamfour.tasc.commons.events.ui.CollapseChangeEvent;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;
import teamfour.tasc.commons.events.ui.ShowHelpRequestEvent;
import teamfour.tasc.commons.events.ui.TaskPanelListChangedEvent;
import teamfour.tasc.commons.events.ui.TaskPanelSelectionChangedEvent;
import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.logic.Logic;
import teamfour.tasc.model.UserPrefs;
import teamfour.tasc.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

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
            mainWindow.getCalendarPanel().changeView(prefs.getCalendarView());

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
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
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCalendarPanel().selectTask(event.getNewSelection());
    }

    /**
     * Handle the event when the task list is changed.
     */
    @Subscribe
    public void handleTaskListPanelChangedEvent(TaskPanelListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCalendarPanel().refreshTasks(event.getNewTaskList());
    }

    /**
     * Handle the event when change calendar view is requested.
     */
    @Subscribe
    public void handleChangeCalendarViewRequestEvent(ChangeCalendarViewRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCalendarPanel().changeView(event.getCalendarViewType());
        prefs.setCalendarView(event.getCalendarViewType());
    }

    //@@author A0127014W
    /**
     * Handle the event when the task list view is collapsed or expanded
     * @param event
     */
    @Subscribe
    public void handleTaskListPanelCollapsedEvent(CollapseChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().setCollapsed(CollapseChangeEvent.getCollapsed());
    }
}
