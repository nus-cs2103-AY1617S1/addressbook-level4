package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.UpcomingReminders;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.task.Task;
import seedu.lifekeeper.MainApp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/lifekeeper.png";

    private Logic logic;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;
    
    private static final long DELAY = 60 * 1000; // one minute
    
    private RefreshTask refreshTask = new RefreshTask();
    private ReminderTask reminderTask = new ReminderTask();
    private Timer refreshTimer = new Timer("Refresh");
    private Timer reminderTimer = new Timer("Reminder");

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
            initRefresh();

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
    
    //==================== Reminder Dialog Box =================================================================
    
    private void showReminderDialog(ArrayList<ReadOnlyActivity> activities) {
        ImageIcon reminderIcon = new ImageIcon(getClass().getResource("/images/ringing.png"),
                "Reminder Bell");
        
        //System.out.println("I'm here");
        
        for (ReadOnlyActivity activity : activities) {
            JOptionPane.showMessageDialog(new JFrame(),
                    textForReminderDialog(activity, activity.getClass().getSimpleName()),
                    "Reminder: " + activity.getName().toString(),
                    JOptionPane.INFORMATION_MESSAGE,
                    reminderIcon);
        }
    }
    
    private String textForReminderDialog(ReadOnlyActivity activity, String type) {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("Reminder for the " + type.toLowerCase() + ": " +  activity.getName());
        
        switch (type) {
        case "Task":
            if (((Task) activity).getDueDate().getCalendarValue() != null) {
                sb.append("\nDue:       " + ((Task) activity).getDueDate().toString());
            }
            sb.append("\nPriority: " + ((Task) activity).getPriority().forReminderDialog());
            break;
        case "Event":
            sb.append("\nTime: " + ((Event) activity).getStartTime().toString()
                    + "\nto        " + ((Event) activity).getEndTime().toString());
            break;
        }
        
        return sb.toString();
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
        mainWindow.getPersonListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event){
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.loadPersonPage(event.getNewSelection());
    }
    
    //==================== Refresh Handling Code =================================================================
    
    private void initRefresh() {
        refreshTimer.cancel();
        reminderTimer.cancel();
        refreshTimer = new Timer("Refresh");
        reminderTimer = new Timer("Reminder");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date executionDate = cal.getTime();
        cal.add(Calendar.SECOND, 1);
        refreshTimer.scheduleAtFixedRate(refreshTask, executionDate, DELAY);
        reminderTimer.scheduleAtFixedRate(reminderTask, cal.getTime(), DELAY);
    }
    
    private class RefreshTask extends TimerTask {
        public void run() {
            mainWindow.refresh();
        }
    }

    private class ReminderTask extends TimerTask {
        public void run() {
            showReminderDialog(UpcomingReminders.popNextReminders());
        }
    }
}
