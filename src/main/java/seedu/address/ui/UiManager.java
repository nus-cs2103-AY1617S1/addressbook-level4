package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.DisplayAliasListEvent;
import seedu.address.commons.events.ui.DisplayTaskListEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ViewTabRequestEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/amethyst_task_manager.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow MainWindow;

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
            MainWindow = MainWindow.load(primaryStage, config, prefs, logic);
            MainWindow.show(); //This should be called before creating other UI parts
            MainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(MainWindow.getCurrentGuiSetting());
        MainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(MainWindow.getPrimaryStage(), type, title, headerText, contentText);
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
        MainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        MainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }
    
    //@@author A0142184L
    @Subscribe
    private void handleDisplayTaskListEvent(DisplayTaskListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
		TaskListPanel listPanel = MainWindow.getTaskListPanel();
		listPanel = TaskListPanel.loadTaskList(MainWindow.primaryStage, MainWindow.getTaskListLeftPlaceholder(), event.list);
    }
    
    @Subscribe
    private void handleDisplayAliasListEvent(DisplayAliasListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        TaskListPanel listPanel = MainWindow.getTaskListPanel();
		listPanel = TaskListPanel.loadAliasList(MainWindow.primaryStage, MainWindow.getTaskListLeftPlaceholder(), event.list);
    }
    
    @Subscribe
    private void handleViewTabRequestEvent(ViewTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.tabName) {
        case TODAY:
            MainWindow.getTabPane().getSelectionModel().select(0);
            break;
        case TOMORROW:
            MainWindow.getTabPane().getSelectionModel().select(1);
            break;
        case WEEK:
            MainWindow.getTabPane().getSelectionModel().select(2);
            break;
        case MONTH:
            MainWindow.getTabPane().getSelectionModel().select(3);
            break;
        case SOMEDAY:
            MainWindow.getTabPane().getSelectionModel().select(4);
            break;
        default:
            MainWindow.getTabPane().getSelectionModel().select(0);
        }
    }
}
