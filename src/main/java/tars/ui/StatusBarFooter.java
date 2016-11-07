package tars.ui;

import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tars.commons.core.LogsCenter;
import tars.commons.events.model.TarsChangedEvent;
import tars.commons.events.storage.TarsStorageDirectoryChangedEvent;
import tars.commons.util.FxViewUtil;
import tars.commons.util.StringUtil;

/**
 * A UI for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart {
    
    private static final double BOUNDARY_PARAMETERS_ZERO = 0.0;
    private static final String SYNC_STATUS_NO_UPDATE =
            "Not updated yet in this session";
    private static final String SYNC_STATUS_UPDATE_INFO = "Last Updated: %s";
    private static final String LOG_MESSAGE_SETTINGS_LAST_UPDATE =
            "Setting last updated status to %s";
    private static final String LOG_MESSAGE_STORAGE_LOCATION_CHANGED =
            "Storage Location Changed: %s";
    private static final String STORAGE_DIRECTORY_INFO = "Storage Directory: %s";
    private static final Logger logger =
            LogsCenter.getLogger(StatusBarFooter.class);
    private static final String FXML = "StatusBarFooter.fxml";
    
    private StatusBar syncStatus;
    private StatusBar saveLocationStatus;
    private GridPane mainPane;

    @FXML
    private AnchorPane saveLocStatusBarPane;

    @FXML
    private AnchorPane syncStatusBarPane;

    private AnchorPane placeHolder;
    private Label saveLocationLabel;
    private Label syncStatusLabel;

    public static StatusBarFooter load(Stage stage, AnchorPane placeHolder,
            String saveLocation) {
        StatusBarFooter statusBarFooter = UiPartLoader.loadUiPart(stage,
                placeHolder, new StatusBarFooter());
        statusBarFooter.configure(saveLocation);
        return statusBarFooter;
    }

    public void configure(String saveLocation) {
        addMainPane();
        addSyncStatus();
        setSyncStatus(SYNC_STATUS_NO_UPDATE);
        addSaveLocation();
        setSaveLocation(String.format(STORAGE_DIRECTORY_INFO, saveLocation));
        registerAsAnEventHandler(this);
    }

    private void addMainPane() {
        FxViewUtil.applyAnchorBoundaryParameters(mainPane,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO);
        placeHolder.getChildren().add(mainPane);
    }

    // @@author A0139924W
    private void setSaveLocation(String location) {
        this.saveLocationLabel.setText(location);
    }

    private void addSaveLocation() {
        this.saveLocationStatus = new StatusBar();
        this.saveLocationLabel = new Label();
        this.saveLocationStatus.setText(StringUtil.EMPTY_STRING);
        this.saveLocationStatus.getRightItems().add(saveLocationLabel);
        FxViewUtil.applyAnchorBoundaryParameters(saveLocationStatus,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO);
        saveLocStatusBarPane.getChildren().add(saveLocationStatus);
    }

    private void setSyncStatus(String status) {
        this.syncStatusLabel.setText(status);
    }

    private void addSyncStatus() {
        this.syncStatus = new StatusBar();
        this.syncStatusLabel = new Label();
        this.syncStatus.setText(StringUtil.EMPTY_STRING);
        this.syncStatus.getLeftItems().add(syncStatusLabel);
        FxViewUtil.applyAnchorBoundaryParameters(syncStatus,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO,
                BOUNDARY_PARAMETERS_ZERO, BOUNDARY_PARAMETERS_ZERO);
        syncStatusBarPane.getChildren().add(syncStatus);
    }

    // @@author

    @Override
    public void setNode(Node node) {
        mainPane = (GridPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane placeholder) {
        this.placeHolder = placeholder;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Subscribe
    public void handleTarsChangedEvent(TarsChangedEvent event) {
        String lastUpdated = (new Date()).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                String.format(LOG_MESSAGE_SETTINGS_LAST_UPDATE, lastUpdated)));
        setSyncStatus(String.format(SYNC_STATUS_UPDATE_INFO, lastUpdated));
    }

    // @@author A0124333U
    @Subscribe
    private void handleTarsStorageChangeDirectoryEvent(
            TarsStorageDirectoryChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setSaveLocation(String.format(LOG_MESSAGE_STORAGE_LOCATION_CHANGED,
                event.getNewFilePath()));
    }
}
