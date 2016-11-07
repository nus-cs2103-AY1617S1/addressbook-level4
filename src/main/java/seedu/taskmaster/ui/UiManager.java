package seedu.taskmaster.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.taskmaster.MainApp;
import seedu.taskmaster.commons.core.ComponentManager;
import seedu.taskmaster.commons.core.Config;
import seedu.taskmaster.commons.core.LogsCenter;
import seedu.taskmaster.commons.events.model.TaskListChangedEvent;
import seedu.taskmaster.commons.events.storage.DataSavingExceptionEvent;
import seedu.taskmaster.commons.events.ui.AgendaTimeRangeChangedEvent;
import seedu.taskmaster.commons.events.ui.JumpToListRequestEvent;
import seedu.taskmaster.commons.events.ui.NavigationSelectionChangedEvent;
import seedu.taskmaster.commons.events.ui.ShowHelpRequestEvent;
import seedu.taskmaster.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskmaster.commons.util.StringUtil;
import seedu.taskmaster.logic.Logic;
import seedu.taskmaster.model.UserPrefs;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/happy_jim_32.png";

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
            mainWindow.switchToInitialTab();
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
    
    @Override
    public void turnOffAutoComplete(){
    	mainWindow.getCommandBox().turnOffAutoComplete();
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
    private void handleShowHelpEvent(ShowHelpRequestEvent event) throws IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getTaskListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.loadTaskPage(event.getNewSelection());
    }
    
    //@@author A0147967J
    @Subscribe
    private void handleNavigationSelectionChangedEvent(NavigationSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.getCommandBox().handleNavigationChanged(mainWindow.getNavbarPanel().getNavigationCommand(event.getNewSelection()));
    }
    
    @Subscribe
    private void handleTaskListChangedEvent(TaskListChangedEvent event){
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	mainWindow.getBrowserPanel().reloadAgenda(event.data.getTaskOccurrenceList());
    }
    
    @Subscribe
    private void handleAgendaTimeRangeChangedEvent(AgendaTimeRangeChangedEvent event){
    	logger.info(LogsCenter.getEventHandlingLogMessage(event));
    	mainWindow.getBrowserPanel().updateAgenda(event.getInputDate(), event.getData());
    }

}
