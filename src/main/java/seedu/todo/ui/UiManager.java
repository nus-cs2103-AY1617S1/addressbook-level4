package seedu.todo.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.todo.MainApp;
import seedu.todo.commons.core.ComponentManager;
import seedu.todo.commons.core.Config;
import seedu.todo.commons.core.LogsCenter;
import seedu.todo.commons.events.storage.DataSavingExceptionEvent;
import seedu.todo.commons.events.ui.*;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.Logic;
import seedu.todo.model.Model;
import seedu.todo.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/app_icon.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    private Model model; 

    public UiManager(Logic logic, Config config, UserPrefs prefs, Model model) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        this.model = model;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logic, model);
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
        alert.getDialogPane().getStylesheets().add("style/DefaultStyle.css");
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
    private void handleExpandCollapseTaskEvent(ExpandCollapseTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTodoListView().toggleExpandCollapsed(event.task);
    }

    //@@author A0139021U
    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getHelpView().displayCommandSummaries(event.getCommandSummaries());
    }
    //@@author

    @Subscribe
    private void handleShowPreviewEvent(ShowPreviewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getHelpView().hideHelpPanel();
        mainWindow.getCommandPreviewView().displayCommandSummaries(event.getPreviewInfo());
    }

    @Subscribe
    private void handleCommandInputEnterEvent(CommandInputEnterEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getHelpView().hideHelpPanel();
        mainWindow.getCommandFeedbackView().clearMessage();
    }

    @Subscribe
    private void handleHighlightTaskEvent(HighlightTaskEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTodoListView().scrollAndSelect(event.getTask());
    }

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

}
