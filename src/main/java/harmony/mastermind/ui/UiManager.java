package harmony.mastermind.ui;

import com.google.common.eventbus.Subscribe;

import harmony.mastermind.MainApp;
import harmony.mastermind.commons.core.ComponentManager;
import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.core.LogsCenter;
import harmony.mastermind.commons.events.storage.DataSavingExceptionEvent;
import harmony.mastermind.commons.events.ui.ExecuteCommandEvent;
import harmony.mastermind.commons.events.ui.JumpToListRequestEvent;
import harmony.mastermind.commons.events.ui.ShowHelpRequestEvent;
import harmony.mastermind.commons.util.StringUtil;
import harmony.mastermind.logic.Logic;
import harmony.mastermind.model.UserPrefs;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Popup;

import java.util.Date;
import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    private final HelpPopup helpPopup;

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
        helpPopup = new HelpPopup();
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

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
        }
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.disposeAutoCompleteBinding();
        mainWindow.hide();
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    //@@author A0139194X
    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        helpPopup.setContent(event.message);
        helpPopup.show(mainWindow.getNode());
    }

    //@@author
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleExecuteCommandEvent(ExecuteCommandEvent event){
        mainWindow.pushToActionHistory(event.title, event.description);
    }
}
